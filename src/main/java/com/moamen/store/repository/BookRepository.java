package com.moamen.store.repository;

import com.moamen.store.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, PagingAndSortingRepository<Book, Long> {
    Page<Book> findAll(Pageable pageable);


    List<Book> findAllByIdIn(List<Long> ids);

    boolean existsByIsbn(String isbn);

    boolean existsById(Long id);

    boolean existsByIdIsNotAndIsbn(Long id, String isbn);

}
