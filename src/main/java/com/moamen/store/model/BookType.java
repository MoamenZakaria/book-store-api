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
public class BookType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type", nullable = false, unique = true)
    private String type;

    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false, columnDefinition = "timestamp(6) default current_timestamp(6)")
    private Instant createdDate;
    @LastModifiedDate
    @Column(name = "updated_date", columnDefinition = "timestamp(6) default current_timestamp(6)")
    private Instant updatedDate;

}
