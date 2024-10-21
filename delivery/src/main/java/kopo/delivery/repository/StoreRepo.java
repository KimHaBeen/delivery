package kopo.delivery.repository;

import kopo.delivery.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepo extends JpaRepository<Store, Long> {

    List<Store> findByCategory_Id(int category_id);

}
