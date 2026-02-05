package com.carlos.trello.persistence.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import lombok.Data;

@Data
@Document(collection = "workspaces")
public class Workspace {
    @Id
    private String id;
    private String name;
    private String description;

    private String ownerId;

    private List<String> memberIds;
}
