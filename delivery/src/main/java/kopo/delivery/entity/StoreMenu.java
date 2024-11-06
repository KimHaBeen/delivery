package kopo.delivery.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "storeMenu")
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StoreMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "STORE_ID", nullable = false)
    private Store store; //Store.java에 StoreId와 FK

    @Column(nullable = false, length = 100)
    private String menuName;

    @Column(nullable = false, length = 10)
    private int menuAmount;

    private String url;


}
