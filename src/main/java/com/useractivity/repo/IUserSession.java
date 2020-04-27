package com.useractivity.repo;

import com.useractivity.model.UserSession;
import com.useractivity.model.UserSessionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserSession extends JpaRepository<UserSession, UserSessionId> {
    Optional<List<UserSession>> findByUserId(String userId);
}
