package com.dochien0204.codeproject.services.Impl;

import com.dochien0204.codeproject.dtos.catalog.CreateNewCatalogDTO;
import com.dochien0204.codeproject.dtos.catalog.UpdateCatalogByIdDTO;
import com.dochien0204.codeproject.entities.Catalog;
import com.dochien0204.codeproject.exceptions.BadRequestException;
import com.dochien0204.codeproject.exceptions.NotFoundException;
import com.dochien0204.codeproject.repositories.CatalogRepository;
import com.dochien0204.codeproject.services.CatalogService;
import com.dochien0204.codeproject.utils.FileUtils;
import com.dochien0204.codeproject.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CatalogServiceImpl implements CatalogService {

  private final CatalogRepository catalogRepository;

  public CatalogServiceImpl(CatalogRepository catalogRepository) {
    this.catalogRepository = catalogRepository;
  }

  @Override
  public List<Catalog> findAllCatalog() {
    return catalogRepository.findAll();
  }

  @Override
  public Catalog findCatalogById(Integer catalogId) {
    Optional<Catalog> catalog = catalogRepository.findById(catalogId);
    if(catalog.isEmpty()) {
      throw new NotFoundException("Not found Catalog has Id " + catalogId);
    }
    return catalog.get();
  }

  @Override
  public Catalog findCatalogByName(String catalogName) {
    Catalog catalog = catalogRepository.findByCatalogName(catalogName);
    if(catalog == null) {
      throw new NotFoundException("Not found catalog has name " + catalogName);
    }
    return catalog;
  }

  @Override
  public void addNewCatalog(CreateNewCatalogDTO catalogDTO) throws IOException {
    if(checkExistsCatalog(catalogDTO.getCatalogName())) {
      throw new BadRequestException(("Catalog already exists!"));
    }
    Catalog catalog = new Catalog();
    catalog.setCatalogName(catalogDTO.getCatalogName());
    if(catalogDTO.getFile() == null ) {
      throw new BadRequestException("Catalog Image is required");
    }
    catalog.setCatalogImage(FileUtils.uploadFile(catalogDTO.getFile()));
    catalog.setCreatedBy(SecurityUtils.getPrincipal());
    catalog.setCreatedDate(System.currentTimeMillis());
    catalogRepository.save(catalog);
  }

  @Override
  public void updateCatalogById(Integer catalogId, UpdateCatalogByIdDTO updateCatalogByIdDTO) throws IOException {
    Optional<Catalog> catalog = catalogRepository.findById(catalogId);
    if(catalog.isEmpty()) {
      throw new NotFoundException("Not found Catalog has Id " + catalogId);
    }
    catalog.get().setCatalogName(updateCatalogByIdDTO.getCatalogName());
    System.out.println(updateCatalogByIdDTO.getFile() == null);
    if(updateCatalogByIdDTO.getFile() != null ) {
      catalog.get().setCatalogImage(FileUtils.uploadFile(updateCatalogByIdDTO.getFile()));
    }
    catalogRepository.save(catalog.get());
  }

  @Override
  public void deleteCatalogById(Integer catalogId) {
    Optional<Catalog> catalog = catalogRepository.findById(catalogId);
    if(catalog.isEmpty()) {
      throw new NotFoundException("Not found Catalog has Id " + catalogId);
    }
    catalogRepository.delete(catalog.get());
  }

  public boolean checkExistsCatalog(String catalogName) {
    Catalog catalog = catalogRepository.findByCatalogName(catalogName);
    if(catalog == null) {
      return false;
    }
    return true;
  }
}
