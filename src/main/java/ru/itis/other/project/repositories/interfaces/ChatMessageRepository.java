package ru.itis.other.project.repositories.interfaces;

import ru.itis.other.project.models.ChatMessage;

import java.util.List;

public interface ChatMessageRepository {

    ChatMessage save(ChatMessage message);

    List<ChatMessage> findAllAfter(long after);
}
