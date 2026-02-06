package com.carlos.trello.mapper;

import org.springframework.stereotype.Component;

import com.carlos.trello.bean.RegisterRequest;
import com.carlos.trello.bean.UserDTO;
import com.carlos.trello.persistence.model.CustomUser;

@Component
public class UserMapper {
    public UserDTO toDTO(CustomUser user) {
        if (user == null) return null;
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        return dto;
    }
    public CustomUser toEntity(UserDTO dto) {
        if (dto == null) return null;
        CustomUser user = new CustomUser();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        return user;
    }
    public CustomUser toEntity(RegisterRequest request) {
        if (request == null) return null;
        CustomUser user = new CustomUser();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        return user;
    }
}
