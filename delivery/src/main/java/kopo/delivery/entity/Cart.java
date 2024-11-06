package kopo.delivery.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private StoreMenu menu;

    private LocalDateTime addTime;

    @PrePersist //DB가 저장되기 직전에 저장하는 시간.
    protected void onCreate() {
        this.addTime = LocalDateTime.now();
    }
}
