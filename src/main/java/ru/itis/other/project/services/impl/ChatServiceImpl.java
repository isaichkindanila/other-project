package ru.itis.other.project.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.async.DeferredResult;
import ru.itis.other.project.dto.chat.MessageDto;
import ru.itis.other.project.dto.chat.MessagesDto;
import ru.itis.other.project.models.ChatMessage;
import ru.itis.other.project.repositories.interfaces.ChatMessageRepository;
import ru.itis.other.project.services.interfaces.AuthService;
import ru.itis.other.project.services.interfaces.ChatService;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class ChatServiceImpl implements ChatService {

    private final AuthService authService;
    private final ChatMessageRepository messageRepository;

    private final Object lock = new Object();
    private final Queue<DeferredResult<MessagesDto>> requests = new LinkedList<>();

    private MessagesDto dtoFrom(List<ChatMessage> messages) {
        var size = messages.size();

        var lastId = size == 0 ? 0L : messages.get(size - 1).getId();
        var messagesDto = messages.stream()
                .map(MessageDto::from)
                .collect(Collectors.toList());

        return new MessagesDto(lastId, messagesDto);
    }

    @Override
    public DeferredResult<MessagesDto> getMessages(long after) {
        var result = new DeferredResult<MessagesDto>();

        synchronized (lock) {
            var newMessages = messageRepository.findAllAfter(after);

            if (newMessages.isEmpty()) {
                result.onTimeout(() -> {
                    // remove 'result' from the queue on timeout
                    synchronized (lock) {
                        requests.remove(result);
                    }
                });

                requests.add(result);
            } else {
                result.setResult(dtoFrom(newMessages));
            }
        }

        return result;
    }

    @Override
    @Transactional
    public void saveMessage(String text) {
        synchronized (lock) {
            var message = messageRepository.save(ChatMessage.builder()
                    .text(text)
                    .sender(authService.getUser())
                    .build());

            var dto = dtoFrom(List.of(message));

            while (!requests.isEmpty()) {
                requests.remove().setResult(dto);
            }
        }
    }
}
