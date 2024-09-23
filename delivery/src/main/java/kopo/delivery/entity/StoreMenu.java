package kopo.delivery.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity(name = "storeMenu")
@Table
@Getter
public class StoreMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "STORE_ID", nullable = false)
    private Store storeId; //Store.java에 StoreId와 FK

    @Column(nullable = false, length = 100)
    private String menuName;

    @Column(nullable = false, length = 10)
    private int menuAmount;

    private String url;
}
