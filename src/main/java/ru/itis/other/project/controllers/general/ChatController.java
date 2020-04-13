package ru.itis.other.project.controllers.general;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import ru.itis.other.project.dto.chat.MessagesDto;
import ru.itis.other.project.services.interfaces.ChatService;

@Controller
@RequestMapping("/chat")
@AllArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public String getChat() {
        return "chat";
    }

    @GetMapping("/messages")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public DeferredResult<MessagesDto> getMessages(@RequestParam long after) {
        return chatService.getMessages(after);
    }

    @PostMapping("/messages")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public ResponseEntity<?> sendMessage(@RequestBody String text) {
        chatService.saveMessage(text);
        return ResponseEntity.ok().build();
    }
}
