package com.web.repository;

import com.web.model.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

  @Query(value = "SELECT * FROM comment AS t WHERE t.news_item_id = ?1", nativeQuery = true)
  List<Comment> findByNewItemId(Long newsItemId);

  @Query(value = "SELECT * FROM comment WHERE user_id = ?1 ORDER BY id DESC LIMIT 1", nativeQuery = true)
  Comment findLastCommentByUserId(long userId);
}
