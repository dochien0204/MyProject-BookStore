package com.dochien0204.codeproject.controllers;

import com.dochien0204.codeproject.base.RestApiV1;
import com.dochien0204.codeproject.base.VsResponseUtil;
import com.dochien0204.codeproject.contants.UrlConstant;
import com.dochien0204.codeproject.dtos.books.CreateNewBookDTO;
import com.dochien0204.codeproject.dtos.books.GetBookItemDTO;
import com.dochien0204.codeproject.dtos.books.UpdateBookByIdDTO;
import com.dochien0204.codeproject.services.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestApiV1
public class BookController {

  private final BookService bookService;
  private final ModelMapper modelMapper;

  public BookController(BookService bookService, ModelMapper modelMapper) {
    this.bookService = bookService;
    this.modelMapper = modelMapper;
  }

  @GetMapping(UrlConstant.Book.LIST)
  public ResponseEntity<?> findAllBook(@RequestParam(name = "pageNo") Integer pageNo,
                                       @RequestParam(name = "pageSize") Integer pageSize,
                                       @RequestParam(name = "sortBy", defaultValue = "bookName") String sortBy) {
    List<GetBookItemDTO> output = bookService.findAllBooks(pageNo, pageSize, sortBy).stream().map(item -> modelMapper.map(item, GetBookItemDTO.class)).collect(Collectors.toList());
    return VsResponseUtil.ok(output);
  }

  @GetMapping(UrlConstant.Book.GET_BY_ID)
  public ResponseEntity<?> findBookById(@PathVariable(name = "bookId") Integer bookId) {
    GetBookItemDTO output = modelMapper.map(bookService.findBookByBookId(bookId), GetBookItemDTO.class);
    return VsResponseUtil.ok(output);
  }

  @GetMapping(UrlConstant.Book.GET_BY_NAME)
  public ResponseEntity<?> findBookByName(@RequestParam(name = "bookName") String bookName) {
    GetBookItemDTO output = modelMapper.map(bookService.findBookByBookName(bookName), GetBookItemDTO.class);
    return VsResponseUtil.ok(output);
  }

  @GetMapping(UrlConstant.Book.GET_BY_SUBNAME)
  public ResponseEntity<?> findBookBySubName(@RequestParam(name = "subName") String subName,
                                             @RequestParam(defaultValue = "0") Integer pageNo,
                                             @RequestParam(defaultValue = "5") Integer pageSize,
                                             @RequestParam(defaultValue = "bookName") String sortBy) {
    List<GetBookItemDTO> output = bookService.findBookBySubName(subName, pageNo, pageSize, sortBy).stream().map(item -> modelMapper.map(item, GetBookItemDTO.class)).collect(Collectors.toList());
    return VsResponseUtil.ok(output);
  }

  @GetMapping(UrlConstant.Book.GET_BY_PRICE_GREATER)
  public ResponseEntity<?> findBookByPriceGreater(@RequestParam(name = "price") Double price,
                                                  @RequestParam(defaultValue = "0") Integer pageNo,
                                                  @RequestParam(defaultValue = "5") Integer pageSize,
                                                  @RequestParam(defaultValue = "bookName") String sortBy) {
    List<GetBookItemDTO> output = bookService.findBookByPriceMoreThan(price, pageNo, pageSize, sortBy).stream().map(item -> modelMapper.map(item, GetBookItemDTO.class)).collect(Collectors.toList());
    return VsResponseUtil.ok(output);
  }

  @GetMapping(UrlConstant.Book.GET_BY_PRICE_LESS)
  public ResponseEntity<?> findBookByPriceLess(@RequestParam(name = "price") Double price,
                                               @RequestParam(defaultValue = "0") Integer pageNo,
                                               @RequestParam(defaultValue = "5") Integer pageSize,
                                               @RequestParam(defaultValue = "bookName") String sortBy) {
    List<GetBookItemDTO> output = bookService.findBookByPriceLessThan(price, pageNo, pageSize, sortBy).stream().map(item -> modelMapper.map(item, GetBookItemDTO.class)).collect(Collectors.toList());
    return VsResponseUtil.ok(output);
  }

  @GetMapping(UrlConstant.Book.GET_BY_CATALOG)
  public ResponseEntity<?> findBookByCatalog(@PathVariable(name = "catalogId") Integer catalogId,
                                      @RequestParam(defaultValue = "0") Integer pageNo,
                                      @RequestParam(defaultValue = "5") Integer pageSize,
                                      @RequestParam(defaultValue = "bookName") String sortBy) {
    List<GetBookItemDTO> output = bookService.findBookByCatalogId(catalogId, pageNo, pageSize, sortBy).stream().map(item -> modelMapper.map(item, GetBookItemDTO.class)).collect(Collectors.toList());
    return VsResponseUtil.ok(output);
  }

  @PostMapping(UrlConstant.Book.CREATE)
  public ResponseEntity<?> createNewBook(@PathVariable(name = "catalogId") Integer catalogId,
                                          @Valid @ModelAttribute CreateNewBookDTO bookDTO) throws IOException {
    bookService.addNewBook(catalogId, bookDTO);
    return VsResponseUtil.ok("Created New Book successfully");
  }

  @PutMapping(UrlConstant.Book.UPDATE_BOOK)
  public ResponseEntity<?> updateBookById(@PathVariable(name = "bookId") Integer bookId,
                                           @ModelAttribute UpdateBookByIdDTO updateBookByIdDTO) throws IOException {
    bookService.updateBookById(bookId, updateBookByIdDTO);
    return VsResponseUtil.ok("Updated Book has ID " + bookId + " successfully");
  }

  @DeleteMapping(UrlConstant.Book.DELETE_BOOK)
  public ResponseEntity<?> deleteBookById(@PathVariable(name = "bookId") Integer bookId) {
    bookService.deleteBookById(bookId);
    return VsResponseUtil.ok("Deleted Book has ID " + bookId + " successfully");
  }
}
