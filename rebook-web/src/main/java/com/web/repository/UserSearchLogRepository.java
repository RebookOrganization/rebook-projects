package com.web.repository;

import com.web.model.UserSearchLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSearchLogRepository extends JpaRepository<UserSearchLog, Long> {

}
