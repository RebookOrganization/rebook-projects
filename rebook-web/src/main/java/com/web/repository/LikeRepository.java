package com.web.repository;

import com.web.model.LikeNews;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<LikeNews, Long> {

  List<LikeNews> findByUserId(Long userId);

  @Query(value = "SELECT * FROM like_news AS t WHERE t.news_item_id = ?1", nativeQuery = true)
  List<LikeNews> findByNewsItemId(Long newsItemId);
}
