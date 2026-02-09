package com.carlos.trello.persistence.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.carlos.trello.persistence.model.Workspace;
import java.util.List;

@Repository
public interface WorkspaceRepository extends MongoRepository<Workspace, String> {
    List<Workspace> findByOwnerId(String ownerId);
    
    List<Workspace> findByMemberIdsContaining(String memberId);
}
