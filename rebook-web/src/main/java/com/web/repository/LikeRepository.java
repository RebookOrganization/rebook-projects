package com.web.repository;

import com.web.model.LikeNews;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<LikeNews, Long> {

  @Query(value= "SELECT * FROM like_news AS t WHERE t.user_id = ?1 AND t.news_item_id = ?2 "
      + "ORDER BY t.id DESC LIMIT 1", nativeQuery = true)
  LikeNews findByNewsItemIdAndUserId(Long userId, Long newsItemId);

  @Query(value = "SELECT * FROM like_news AS t WHERE t.news_item_id = ?1 AND t.is_like = 1", nativeQuery = true)
  List<LikeNews> findLikeNewsByNewsItemId(Long newsItemId);

  @Query(value = "SELECT * FROM like_news AS t WHERE t.is_like=1 AND t.user_id=?1 ORDER BY id DESC LIMIT 1", nativeQuery = true)
  LikeNews findLastLikeByUserId(long userId);

}
