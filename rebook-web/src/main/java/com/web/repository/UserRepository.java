package com.web.repository;

import com.web.model.User;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    Optional<User> findByName(String name);

    @Query(value = "SELECT * FROM realEstateSchema.user as t WHERE t.name LIKE %?1% ", nativeQuery = true)
    List<User> findByNameLike(String name);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "update realEstateSchema.user u set u.last_login = ?1, u.ip_login = ?2 where u.email = ?3", nativeQuery = true)
    void updateLogin(Date timeLogin, String ipLogin, String email);

    @Modifying
    @Query("update User u set u.password = :password where u.id = :id")
    void updatePassword(@Param("password") String password, @Param("id") Long id);

    @Modifying
    @Query("update User u set u.name= ?2, u.imageUrl = ?3, u.phoneNumber = ?4, "
        + "u.birthDate = ?5, u.gender = ?6 where u.id = ?1")
    void updateUserProfile(Long id, String name, String imageUrl, String phoneNum, String birthDate, String gender);
}
