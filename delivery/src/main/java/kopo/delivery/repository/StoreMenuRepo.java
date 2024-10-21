package kopo.delivery.repository;

import kopo.delivery.entity.Store;
import kopo.delivery.entity.StoreMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreMenuRepo extends JpaRepository<StoreMenu, Long> {

    List<StoreMenu> findByStore_storeID(Long storeId);

}
