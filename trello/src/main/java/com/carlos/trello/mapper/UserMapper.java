package com.carlos.trello.mapper;

import org.springframework.stereotype.Component;

import com.carlos.trello.bean.UserDTO;
import com.carlos.trello.persistence.model.User;

@Component
public class UserMapper {
    public UserDTO toDTO(User user) {
        if (user == null) return null;
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        return dto;
    }
    public User toEntity(UserDTO dto) {
        if (dto == null) return null;
        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        return user;
    }
}
