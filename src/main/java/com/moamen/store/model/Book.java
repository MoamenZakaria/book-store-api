package com.moamen.store.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "author", nullable = false)
    private String author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_type_id", nullable = false)
    private BookType bookType;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "isbn", nullable = false, unique = true)
    private String isbn;
    @CreationTimestamp
    @Column(name = "created_date", nullable = false, updatable = false, columnDefinition = "timestamp(6) default current_timestamp(6)")
    private Instant createdDate;
    @UpdateTimestamp
    @Column(name = "updated_date", columnDefinition = "timestamp(6) default current_timestamp(6)")
    private Instant updatedDate;

}
