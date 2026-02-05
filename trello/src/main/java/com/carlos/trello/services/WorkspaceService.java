package com.carlos.trello.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.carlos.trello.persistence.repo.UserRepository;
import com.carlos.trello.persistence.repo.WorkspaceRepository;
import com.carlos.trello.persistence.model.Workspace;
import java.util.List;
import java.util.Optional;

public class WorkspaceService {
    @Autowired
    WorkspaceRepository workspaceRepository;

    @Autowired
    UserRepository userRepository;

    public Workspace createWorkspace(String name, String description, String ownerId) {
        // Validate owner exists
        if (ownerId.isEmpty() ||!userRepository.existsById(ownerId)) {
            throw new IllegalArgumentException("Owner not found");
        }

        Workspace workspace = new Workspace();
        workspace.setName(name);
        workspace.setDescription(description);
        workspace.setOwnerId(ownerId);
        workspace.setMemberIds(List.of(ownerId)); // Owner is also a member

        return workspaceRepository.save(workspace);
    }

    public List<Workspace> getWorkspacesByUserId(String userId) {
        return workspaceRepository.findByMemberIdsContaining(userId);
    }

    public Optional<Workspace> getWorkspaceById(String workspaceId) {
        return workspaceRepository.findById(workspaceId);
    }

    /* Check for ROLES when adding members */
    public Workspace addMemberToWorkspace(String workspaceId, String userId){
        Workspace workspace = workspaceRepository.findById(workspaceId)
        .orElseThrow(() -> new IllegalArgumentException("Workspace not found"));

        if (!workspace.getMemberIds().contains(userId)) {
            workspace.getMemberIds().add(userId);
            return workspaceRepository.save(workspace);
        }
        return workspace;
    }
}
