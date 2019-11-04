package com.web.repository;

import com.web.model.PropertyAddress;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PropertyAdressRepository extends JpaRepository<PropertyAddress, Long> {

  @Query(value = "SELECT * FROM property_address as t WHERE t.summary LIKE %?1% ", nativeQuery = true)
  List<PropertyAddress> findAllBySummary(String address);

  @Query(value = "SELECT * FROM property_address?1 as t WHERE t.summary LIKE %?2% ", nativeQuery = true)
  List<PropertyAddress> findAllBySummaryPartition(int partition, String summary);

  @Modifying
  @Transactional
  @Query(value = "insert into property_address?1 (district, province, street, summary) "
      + "values (?2, ?3, ?4, ?5)", nativeQuery = true)
  void saveByPartition(int partition, String district, String province, String street, String summary);

  @Query(value = "select * from property_address?1 order by id desc limit 1", nativeQuery = true)
  PropertyAddress findLastRow(int partition);

  @Query(value = "select * from property_address?1 as t where t.id = ?2", nativeQuery = true)
  Optional<PropertyAddress> findById(int partition, Long id);
}
