package com.dochien0204.codeproject.controllers;

import com.dochien0204.codeproject.base.RestApiV1;
import com.dochien0204.codeproject.base.VsResponseUtil;
import com.dochien0204.codeproject.contants.UrlConstant;
import com.dochien0204.codeproject.dtos.catalog.CreateNewCatalogDTO;
import com.dochien0204.codeproject.dtos.catalog.GetCatalogItemDTO;
import com.dochien0204.codeproject.dtos.catalog.UpdateCatalogByIdDTO;
import com.dochien0204.codeproject.services.CatalogService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestApiV1
public class CatalogController {

  private final CatalogService catalogService;

  private final ModelMapper modelMapper;

  public CatalogController(CatalogService catalogService, ModelMapper modelMapper) {
    this.catalogService = catalogService;
    this.modelMapper = modelMapper;
  }

  @GetMapping(UrlConstant.Catalog.LIST)
  public ResponseEntity<?> findAllCatalog() {
    List<GetCatalogItemDTO> output = catalogService.findAllCatalog().stream().map(item -> modelMapper.map(item, GetCatalogItemDTO.class)).collect(Collectors.toList());
    return VsResponseUtil.ok(output);
  }

  @GetMapping(UrlConstant.Catalog.GET_BY_ID)
  public ResponseEntity<?> findCatalogById(@PathVariable(name = "catalogId") Integer catalogId) {
    GetCatalogItemDTO output = modelMapper.map(catalogService.findCatalogById(catalogId), GetCatalogItemDTO.class);
    return VsResponseUtil.ok(output);
  }

  @GetMapping(UrlConstant.Catalog.GET_BY_CATALOG_NAME)
  public ResponseEntity<?> findCatalogByName(@RequestParam(name = "catalogName") String catalogName) {
    GetCatalogItemDTO output = modelMapper.map(catalogService.findCatalogByName(catalogName), GetCatalogItemDTO.class);
    return VsResponseUtil.ok(output);
  }

  @PostMapping(UrlConstant.Catalog.CREATE)
  public ResponseEntity<?> createNewCatalog(@ModelAttribute CreateNewCatalogDTO catalogDTO) throws IOException {
    catalogService.addNewCatalog(catalogDTO);
    return VsResponseUtil.ok("Create Catalog successfully");
  }

  @PutMapping(UrlConstant.Catalog.UPDATE)
  public ResponseEntity<?> updateCatalogById(@PathVariable(name = "catalogId") Integer catalogId,
                                             @ModelAttribute UpdateCatalogByIdDTO catalogDTO) throws IOException {
    catalogService.updateCatalogById(catalogId, catalogDTO);
    return VsResponseUtil.ok("Updated Catalog successfully");
  }

  @DeleteMapping(UrlConstant.Catalog.DELETE)
  public ResponseEntity<?> deleteCatalogById(@PathVariable(name = "catalogId") Integer catalogId) {
    catalogService.deleteCatalogById(catalogId);
    return VsResponseUtil.ok("Deleted Catalog successfully");
  }
}
