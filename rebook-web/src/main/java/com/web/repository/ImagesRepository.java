package com.web.repository;

import com.web.model.NewsImageUrl;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ImagesRepository extends JpaRepository<NewsImageUrl, Long> {

  @Modifying
  @Transactional
  @Query(value = "INSERT INTO images?6 (image_size, image_type, image_url, pic_byte, news_item_id) "
      + "VALUES (?1, ?2, ?3, ?4, ?5) ", nativeQuery = true)
  void saveByPartition(Long image_size, String image_type, String image_url, byte[] pic_byte, Long news_item_id, int partition);

  @Query(value = "SELECT * FROM images?1 WHERE id=?2", nativeQuery = true)
  Optional<NewsImageUrl> findByIdAndPartition(int partition, long id);

  @Query(value = "SELECT * FROM images?1 ORDER BY id DESC LIMIT 1", nativeQuery = true)
  NewsImageUrl findLastRow(int partition);

}
