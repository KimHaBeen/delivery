package kopo.delivery.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity(name = "store")
@Getter
@Setter
@Table
public class Store {

    @Id
    @Column(name = "STORE_ID", nullable = false)
    private Long storeID;

    @Column(nullable = false)
    private String storeName;

    @Column(nullable = false)
    private int storeMinAmount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CATEGORY_REF_ID", nullable = false)
    private Category category;

    private String url;

    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<StoreMenu> menus;

}
