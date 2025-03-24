package com.gn.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gn.mvc.entity.ChatMsg;

public interface ChatMsgRepository extends JpaRepository<ChatMsg, Long> {
	
}
