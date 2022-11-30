package com.dochien0204.codeproject.repositories;

import com.dochien0204.codeproject.entities.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogRepository extends JpaRepository<Catalog, Integer> {

  @Query("select c from Catalog c where c.catalogName = ?1")
  Catalog findByCatalogName(String catalogName);
}
