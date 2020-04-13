package ru.itis.other.project.services;

import org.springframework.web.context.request.async.DeferredResult;
import ru.itis.other.project.dto.chat.MessagesDto;
import ru.itis.other.project.util.annotations.DoNotLog;

public interface ChatService {

    @DoNotLog DeferredResult<MessagesDto> getMessages(long after);

    void saveMessage(String text);
}
