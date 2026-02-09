package com.carlos.trello.bean;

import java.util.List;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class WorkspaceDTO {
    private String id;
    private String name;
    private String description;

    private String ownerId;

    private List<String> memberIds;

    public WorkspaceDTO() {
    }

    public WorkspaceDTO(String id, String name, String description, String ownerId, List<String> memberIds) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.ownerId = ownerId;
        this.memberIds = memberIds;
    }
}
