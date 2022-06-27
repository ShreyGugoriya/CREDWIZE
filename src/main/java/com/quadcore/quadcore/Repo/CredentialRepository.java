package com.quadcore.quadcore.Repo;

import com.quadcore.quadcore.Entities.Credential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface CredentialRepository extends JpaRepository<Credential, Integer> {
    Optional<Credential> findByUsername(String username);
    Boolean existsByUsername(String username);

}

