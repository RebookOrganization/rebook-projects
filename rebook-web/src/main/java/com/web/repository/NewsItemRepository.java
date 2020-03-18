package com.web.repository;

import com.web.model.NewsItem;
import com.web.model.PropertyAddress;
import com.web.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface NewsItemRepository extends JpaRepository<NewsItem, Long> {

    Optional<NewsItem> findById(Long id);

    List<NewsItem> findAllByUser(User user);

    @Query(value = "SELECT * FROM news_item?1 WHERE id = ?2", nativeQuery = true)
    Optional<NewsItem> findByPartitionAndId(int partition, long id);

    @Query(value = "SELECT * FROM news_item as t where t.property_address_id = ?1", nativeQuery = true)
    NewsItem findByPropertyAddress(long propertyAddress);

    @Query(value = "SELECT * FROM news_item?1 WHERE property_address_id IN (?2)", nativeQuery = true)
    List<NewsItem> findAllByAddressIdAndPartition(int partition, List<Long> addressIdList);

    @Query(value = "SELECT * FROM news_item as t where t.posted_milisec > ?1 and t.posted_milisec <= ?2", nativeQuery = true)
    List<NewsItem> findAllByPostedDate(long dateFrom, long dateTo);

    @Query(value = "SELECT * FROM news_item as t where t.posted_milisec = ?1", nativeQuery = true)
    List<NewsItem> findAllByPostedMilisec(long milisec);

    @Query(value = "SELECT * FROM news_item?1 ORDER BY id DESC LIMIT 20", nativeQuery = true)
    List<NewsItem> findNewsByPartition(int partition);

    @Query(value = "SELECT * FROM news_item?1 ORDER BY id DESC LIMIT 1", nativeQuery = true)
    NewsItem findLastRow(int partition);

    @Modifying
    @Query(value = "INSERT INTO news_item?25 (area, balcony, description, direct_of_house, floor_number, front_end, "
        + "interior, posted_date, posted_milisec, price, pub_date, room_number, summary, title, toilet_number, "
        + "trans_type, url, wardin, contact_owner_id, property_address_id, property_project_id, user_id, area_num, price_num) values (?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10, ?11,"
        + " ?12, ?13, ?14, ?15, ?16, ?17, ?18, ?19, ?20, ?21, ?22, ?23, ?24) ", nativeQuery = true)
    void saveToPartition(String area, String balcony, String description, String direct_of_house, String floor_number, String front_end,
            String interior, String posted_date, Long posted_milisec, String price, String pub_date, String room_number, String summary, String title,
            String toilet_number, String trans_type, String url, String wardin, Long contact_owner_id, Long property_address_id,
            Long property_project_id, Long user_id, float area_num, double price_num, int partition);

    @Modifying
    @Query(value = "UPDATE news_item?1 SET property_address_id=?3, contact_owner_id=?4, property_project_id=?5, url=?6 WHERE id=?2", nativeQuery = true)
    void updateNewsPartition(int partition, long newsId, long addressId, long contactId, long projectId, String url);

    @Query(value = "SELECT * FROM news_item?1 AS t WHERE t.price_num >= ?2 AND t.price_num <= ?3", nativeQuery = true)
    List<NewsItem> findAllByPriceNum(int partition, int priceFrom, int priceTo);

    @Query(value = "SELECT * FROM news_item?1 AS t WHERE t.area_num >= ?2 AND t.area_num <= ?3", nativeQuery = true)
    List<NewsItem> findAllByAreaNum(int partition, int areaFrom, int areaTo);
}
