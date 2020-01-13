package com.web.repository;

import com.web.model.ShareNews;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShareRepository extends JpaRepository<ShareNews, Long> {

  @Query(value = "SELECT * FROM share_news AS t WHERE t.news_item_id = ?1 AND t.is_share = 1", nativeQuery = true)
  List<ShareNews> findByNewItemId(Long newsItemId);

  @Query(value = "SELECT * FROM share_news AS t WHERE t.news_item_id=?1 AND t.user_id=?2", nativeQuery = true)
  ShareNews findByNewItemIdAndUserId(Long newsId, Long userId);

}
