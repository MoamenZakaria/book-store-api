package com.moamen.store.repository;

import com.moamen.store.model.BookType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookTypeRepository extends JpaRepository<BookType, Long> {

    BookType findByTypeIgnoreCase(String type);

    boolean existsById(Long id);

}
