package kopo.delivery.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Table
@Entity
@Getter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATEGORY_ID", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String category;
}
