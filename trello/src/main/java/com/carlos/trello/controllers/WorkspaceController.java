package com.carlos.trello.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.carlos.trello.bean.WorkspaceDTO;
import com.carlos.trello.config.UserDetailsImpl;
import com.carlos.trello.mapper.WorkspaceMapper;
import com.carlos.trello.persistence.model.Workspace;
import com.carlos.trello.services.WorkspaceService;

@RestController
@RequestMapping("/api/workspaces")
public class WorkspaceController {
    @Autowired
    private WorkspaceService workspaceService;

    @Autowired
    private WorkspaceMapper workspaceMapper;

    @GetMapping
    public List<WorkspaceDTO> getMyWorkspaces(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return workspaceService.getWorkspacesByUserId(userDetails.getId())
        .stream()
        .map(workspaceMapper::toDTO)
        .toList();
    }

    @PostMapping
    public WorkspaceDTO createWorkspace(@RequestBody WorkspaceDTO workspaceDTO,@AuthenticationPrincipal UserDetailsImpl userDetails){
        Workspace workspace = workspaceService.createWorkspace(workspaceDTO.getName(), workspaceDTO.getDescription(),userDetails.getId());
        return workspaceMapper.toDTO(workspace);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkspaceDTO> getWorkspace(@PathVariable String id){
        return workspaceService.getWorkspaceById(id)
            .map(workspaceMapper::toDTO)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
