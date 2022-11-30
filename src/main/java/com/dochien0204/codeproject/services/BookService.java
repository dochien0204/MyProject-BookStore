package com.dochien0204.codeproject.services;

import com.dochien0204.codeproject.dtos.books.CreateNewBookDTO;
import com.dochien0204.codeproject.dtos.books.UpdateBookByIdDTO;
import com.dochien0204.codeproject.entities.Book;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;

public interface BookService {
  List<Book> findAllBooks(Integer pageNo, Integer pageSize, String sortBy);
  Book findBookByBookId(Integer bookId);
  Book findBookByBookName(String bookName);
  List<Book> findBookBySubName(String subName, Integer pageNo, Integer pageSize, String sortBy);
  List<Book> findBookByAuthor(String author);
  List<Book> findBookByPublisher(String publisher);
  List<Book> findBookByPriceMoreThan(double price, Integer pageNo, Integer pageSize, String sortBy);
  List<Book> findBookByPriceLessThan(double price, Integer pageNo, Integer pageSize, String sortBy);
  List<Book> findBookByCatalogId(Integer catalogId, Integer pageNo, Integer pageSize, String sortBy);
  void addNewBook(Integer catalogId, CreateNewBookDTO bookDTO) throws IOException;
  void updateBookById(Integer bookId ,UpdateBookByIdDTO updateBookByIdDTO) throws IOException;
  void deleteBookById(Integer bookId);
}
