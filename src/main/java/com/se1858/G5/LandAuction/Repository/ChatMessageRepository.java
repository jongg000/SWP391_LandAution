package com.se1858.G5.LandAuction.Repository;

import com.se1858.G5.LandAuction.Entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, String> {
    List<ChatMessage> findByChatId(String chatId);
}
