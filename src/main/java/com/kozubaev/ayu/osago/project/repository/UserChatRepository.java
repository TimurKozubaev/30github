package com.kozubaev.ayu.osago.project.repository;

import com.kozubaev.ayu.osago.project.model.UserChat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserChatRepository extends JpaRepository<UserChat, Long> {
    UserChat findByPhoneNumber(String phoneNumber);
}