package ru.itis.other.project.dto.chat;

import lombok.Value;

import java.util.List;

@Value
public class MessagesDto {
    long lastId;
    List<MessageDto> messages;
}
