package ru.itis.other.project.dto.chat;

import lombok.Value;
import ru.itis.other.project.models.ChatMessage;

@Value
public class MessageDto {
    long id;
    String text;

    public static MessageDto from(ChatMessage message) {
        return new MessageDto(message.getId(), message.getText());
    }
}
