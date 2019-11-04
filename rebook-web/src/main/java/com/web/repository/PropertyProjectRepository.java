package com.web.repository;

import com.web.model.PropertyProject;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PropertyProjectRepository extends JpaRepository<PropertyProject, Long> {

  @Modifying
  @Transactional
  @Query(value = "insert into property_project?1 (project_name, project_owner, project_size) "
      + "values (?2, ?3, ?4)", nativeQuery = true)
  void saveByPartition(int partition, String project_name, String project_owner, String project_size);

  @Query(value = "select * from property_project?1 order by id desc limit 1", nativeQuery = true)
  PropertyProject findLastRow(int partition);

  @Query(value = "select * from property_project?1 as t where t.id = ?2", nativeQuery = true)
  Optional<PropertyProject> findById(int partition, Long id);

}
