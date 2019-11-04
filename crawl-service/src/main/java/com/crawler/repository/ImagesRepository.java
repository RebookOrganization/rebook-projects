package com.crawler.repository;

import com.crawler.model.NewsImageUrl;
import java.util.List;
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

}
