package com.chris.hotelmanagementsystem.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface OEntityRepository<T extends OEntity> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {

  @Query("""
      select e from #{#entityName} e
      where e.keyword ilike concat('%', :query, '%')
      """)
  Page<T> findAll(String query, Pageable pageable);

}
