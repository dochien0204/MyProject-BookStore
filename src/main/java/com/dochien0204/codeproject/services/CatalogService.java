package com.dochien0204.codeproject.services;

import com.dochien0204.codeproject.dtos.catalog.CreateNewCatalogDTO;
import com.dochien0204.codeproject.dtos.catalog.UpdateCatalogByIdDTO;
import com.dochien0204.codeproject.entities.Catalog;

import java.io.IOException;
import java.util.List;

public interface CatalogService {
  List<Catalog> findAllCatalog();
  Catalog findCatalogById(Integer catalogId);
  Catalog findCatalogByName(String name);
  void addNewCatalog(CreateNewCatalogDTO catalogDTO) throws IOException;
  void updateCatalogById(Integer catalogId, UpdateCatalogByIdDTO updateCatalogByIdDTO) throws IOException;
  void deleteCatalogById(Integer catalogId);
}
