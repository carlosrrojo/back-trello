package com.carlos.trello.mapper;

import org.springframework.stereotype.Component;

import com.carlos.trello.bean.WorkspaceDTO;
import com.carlos.trello.persistence.model.Workspace;


@Component
public class WorkspaceMapper {
    public WorkspaceDTO toDTO(Workspace workspace) {
        if (workspace == null) {
            return null;
        }
        return new WorkspaceDTO(
            workspace.getId(),
            workspace.getName(),
            workspace.getDescription(),
            workspace.getOwnerId(),
            workspace.getMemberIds()
        );
    }

    public Workspace toEntity(WorkspaceDTO workspaceDTO) {
        if (workspaceDTO == null) {
            return null;
        }
        Workspace workspace = new Workspace();
        workspace.setId(workspaceDTO.getId());
        workspace.setName(workspaceDTO.getName());
        workspace.setDescription(workspaceDTO.getDescription());
        workspace.setOwnerId(workspaceDTO.getOwnerId());
        workspace.setMemberIds(workspaceDTO.getMemberIds());
        return workspace;
    }    
}
