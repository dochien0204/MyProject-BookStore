package com.dochien0204.codeproject.services.Impl;

import com.dochien0204.codeproject.dtos.books.CreateNewBookDTO;
import com.dochien0204.codeproject.dtos.books.UpdateBookByIdDTO;
import com.dochien0204.codeproject.entities.Book;
import com.dochien0204.codeproject.entities.Catalog;
import com.dochien0204.codeproject.entities.Pagination;
import com.dochien0204.codeproject.exceptions.BadRequestException;
import com.dochien0204.codeproject.exceptions.NotFoundException;
import com.dochien0204.codeproject.repositories.BookRepository;
import com.dochien0204.codeproject.repositories.CatalogRepository;
import com.dochien0204.codeproject.services.BookService;
import com.dochien0204.codeproject.utils.FileUtils;
import com.dochien0204.codeproject.utils.SecurityUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class BookServiceImpl implements BookService {

  private final BookRepository bookRepository;
  private final ModelMapper modelMapper;

  private final CatalogRepository catalogRepository;

  public BookServiceImpl(BookRepository bookRepository, ModelMapper modelMapper, CatalogRepository catalogRepository) {
    this.bookRepository = bookRepository;
    this.modelMapper = modelMapper;
    this.catalogRepository = catalogRepository;
  }


  @Override
  public Map<String, Object> findAllBooks(Integer pageNo, Integer pageSize, String sortBy) {
    Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
    Page<Book> pagedResult = bookRepository.findAll(paging);
    Map<String, Object> result = new HashMap<>();
    if (pagedResult.hasContent()) {
      result = addToMap(pagedResult.getTotalElements(), pagedResult.getContent());
    }
    return result;
  }

  @Override
  public Book findBookByBookId(Integer bookId) {
    Optional<Book> book = bookRepository.findById(bookId);
    if (book.isEmpty()) {
      throw new NotFoundException("Not found Book has ID " + bookId);
    }
    return book.get();
  }

  @Override
  public Book findBookByBookName(String bookName) {
    Book book = bookRepository.findByBookName(bookName);
    if (book == null) {
      throw new NotFoundException("Not found book has name " + bookName);
    }
    return book;
  }

  @Override
  public List<Book> findBookBySubName(String subName, Integer pageNo, Integer pageSize, String sortBy) {
    Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
    Page<Book> pageResult = bookRepository.findBySubName(subName, paging);
    if (pageResult.hasContent()) {
      return pageResult.getContent();
    }
    return new ArrayList<Book>();
  }

  @Override
  public List<Book> findBookByAuthor(String author) {
    return null;
  }

  @Override
  public List<Book> findBookByPublisher(String publisher) {
    return null;
  }

  @Override
  public List<Book> findBookByPriceMoreThan(double price, Integer pageNo, Integer pageSize, String sortBy) {
    Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
    Page<Book> pageResult = bookRepository.findBookByPriceGreaterThan(price, paging);
    if (pageResult.hasContent()) {
      return pageResult.getContent();
    }
    return new ArrayList<Book>();
  }

  @Override
  public List<Book> findBookByPriceLessThan(double price, Integer pageNo, Integer pageSize, String sortBy) {
    Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
    Page<Book> pageResult = bookRepository.findBookByPriceLessThan(price, paging);
    if (pageResult.hasContent()) {
      return pageResult.getContent();
    }
    return new ArrayList<Book>();
  }

  @Override
  public Map<String,Object> findBookByCatalogId(Integer catalogId, Integer pageNo, Integer pageSize, String sortBy) {
    Optional<Catalog> catalog = catalogRepository.findById(catalogId);
    if (catalog.isEmpty()) {
      throw new NotFoundException("Not Found catalog has ID " + catalogId);
    }
    Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
    Page<Book> pageResult = bookRepository.findBookByCatalog(catalog.get(), paging);
    Map<String, Object> result = new HashMap<>();
    if (pageResult.hasContent()) {
      result = addToMap(pageResult.getTotalElements(), pageResult.getContent());
    }
    return result;
  }

  @Override
  public void addNewBook(Integer catalogId, CreateNewBookDTO bookDTO) throws IOException {
    if (checkExistsBook(bookDTO.getBookName())) {
      throw new BadRequestException("Book already exists");
    }
    Optional<Catalog> catalog = catalogRepository.findById(catalogId);
    if (catalog.isEmpty()) {
      throw new NotFoundException("Not found catalog " + catalogId);
    }
    Book book = modelMapper.map(bookDTO, Book.class);
    if (bookDTO.getFile() == null) {
      throw new BadRequestException("Book image is required");
    }
    book.setImage(FileUtils.uploadFile(bookDTO.getFile()));
    book.setCatalog(catalog.get());
    book.setActiveFlag(true);
    book.setDeleteFlag(false);
    book.setCreatedBy(SecurityUtils.getPrincipal());
    book.setCreatedDate(System.currentTimeMillis());
    bookRepository.save(book);
  }

  @Override
  public void updateBookById(Integer bookId, UpdateBookByIdDTO updateBookByIdDTO) throws IOException {
    Optional<Book> book = bookRepository.findById(bookId);
    if(book.isEmpty()) {
      throw new NotFoundException("Not Found Book has ID " + bookId);
    }
    Optional<Catalog> catalog = catalogRepository.findById(updateBookByIdDTO.getCatalogId());
    if(catalog.isEmpty()) {
      throw new NotFoundException("Not found Catalog has ID " + updateBookByIdDTO.getCatalogId());
    }
    if(checkExistsBook(updateBookByIdDTO.getBookName())) {
      throw new BadRequestException("Book already exists");
    }
    book.get().setBookName(updateBookByIdDTO.getBookName());
    book.get().setAvailable(updateBookByIdDTO.getAvailable());
    book.get().setPrice(updateBookByIdDTO.getPrice());
    if(updateBookByIdDTO.getFile() != null) {
      book.get().setImage(FileUtils.uploadFile(updateBookByIdDTO.getFile()));
    }
    book.get().setDescription(updateBookByIdDTO.getDescription());
    book.get().setAuthor(updateBookByIdDTO.getAuthor());
    book.get().setPublisher(updateBookByIdDTO.getPublisher());
    book.get().setCatalog(catalog.get());
    book.get().setLastModifiedBy(SecurityUtils.getPrincipal());
    book.get().setLastModifiedDate(System.currentTimeMillis());
    bookRepository.save(book.get());
  }

  @Override
  public void deleteBookById(Integer bookId) {
    Optional<Book> book = bookRepository.findById(bookId);
    if(book.isEmpty()) {
      throw new NotFoundException("Not found book has ID " + bookId);
    }
    bookRepository.deleteById(bookId);
  }

  public boolean checkExistsBook(String bookName) {
    Book book = bookRepository.findByBookName(bookName);
    if (book == null) {
      return false;
    }
    return true;
  }

  public Map<String, Object> addToMap(long totalNumbers, List<Book> list) {
    Map<String, Object> map = new HashMap<>();
    map.put("totalNumbers", totalNumbers);
    map.put("list", list);
    return map;
  }
}
