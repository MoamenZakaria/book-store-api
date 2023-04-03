package com.moamen.store.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_type_id", referencedColumnName = "id", nullable = false)
    private BookType bookTypeId;

    @Column(name = "discount_percent", nullable = false)
    private Double discountPercent;

    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false, columnDefinition = "timestamp(6) default current_timestamp(6)")
    private Instant createdDate;
    @LastModifiedDate
    @Column(name = "updated_date", columnDefinition = "timestamp(6) default current_timestamp(6)")
    private Instant updatedDate;

}
