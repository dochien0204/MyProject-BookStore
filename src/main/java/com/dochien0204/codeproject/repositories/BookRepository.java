package com.dochien0204.codeproject.repositories;

import com.dochien0204.codeproject.entities.Book;
import com.dochien0204.codeproject.entities.Catalog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends PagingAndSortingRepository<Book, Integer> {

  @Query("select b from Book b")
  Page<Book> findAll(Pageable pageable);

  @Query("select b from Book b where b.bookName = ?1")
  Book findByBookName(String bookName);

  @Query("select b from Book b where b.bookName like %?1%")
  Page<Book> findBySubName(String subName, Pageable pageable);

  @Query("select b from Book b where b.price >= ?1")
  Page<Book> findBookByPriceGreaterThan(double price, Pageable pageable);

  @Query("select b from Book b where b.price <= ?1")
  Page<Book> findBookByPriceLessThan(double price, Pageable pageable);

  @Query("select b from Book b where b.catalog = ?1")
  Page<Book> findBookByCatalog(Catalog catalog, Pageable pageable);

}
