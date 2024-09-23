package kopo.delivery.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Table
@Entity(name = "store")
public class Store {

    @Id
    @Column(name = "STORE_ID", nullable = false)
    private Long storeID;

    @Column(nullable = false)
    private String storeName;

    @Column(nullable = false)
    private int storeMinAmount;

    @JoinColumn(name = "CATEGORY_ID", nullable = false)
    private String category;
}
