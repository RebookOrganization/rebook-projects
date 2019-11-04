package com.crawler.repository;

import com.crawler.model.ContactOwner;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ContactOwnerRepository extends JpaRepository<ContactOwner, Long> {

  @Modifying
  @Transactional
  @Query(value = "INSERT INTO contact_owner?1 (`address`, `contact_name`, `email`, `phone_number`) "
      + "VALUES (?2, ?3, ?4, ?5)", nativeQuery = true)
  void insertWithPartition(int partition, String address, String contact_name, String email, String phone_number);

  @Query(value = "select * from contact_owner?1 order by id desc limit 1", nativeQuery = true)
  ContactOwner findLastRow(int partition);

  @Query(value = "select * from contact_owner?1 as t where t.id = ?2", nativeQuery = true)
  Optional<ContactOwner> findById(int partition, Long id);
}
