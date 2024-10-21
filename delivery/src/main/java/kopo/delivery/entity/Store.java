package kopo.delivery.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CATEGORY_ID", nullable = false)
    private Category category;
}
