package com.web.repository;

import com.web.model.EmailVerifyToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("emailVerifyRepository")
public interface EmailVerifyRepository extends JpaRepository<EmailVerifyToken, Long> {
  EmailVerifyToken findByVerifyToken(String verifyToken);
}
