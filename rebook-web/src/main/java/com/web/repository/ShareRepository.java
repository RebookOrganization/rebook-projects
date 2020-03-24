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

  @Query(value = "SELECT * FROM share_news WHERE user_id = ?1 ORDER BY id DESC LIMIT 1", nativeQuery = true)
  ShareNews findLastShareByUserId(long userId);

  @Query(value = "SELECT * FROM share_news as t WHERE t.user_id = ?1 and t.is_share=1", nativeQuery = true)
  List<ShareNews> findAllByUserId(long userId);

//  @Query(value = "SELECT * FROM share_news as t WHERE t.user_id = ?1 and "
//      + "t.is_share = 1 and t.partition = ?2", nativeQuery = true)
//  List<ShareNews> findAllByUserId(long userId, int partition);
}
