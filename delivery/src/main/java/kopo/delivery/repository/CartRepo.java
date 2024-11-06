package kopo.delivery.repository;

import kopo.delivery.dto.CartDTO;
import kopo.delivery.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartRepo extends JpaRepository<Cart, Long> {

    @Query("SELECT c.menu.id, c.menu.menuName, c.menu.menuAmount, count(c) FROM Cart c GROUP BY c.menu")
    List<Object[]> countCartMenu();

    @Query("SELECT MAX(c.id) FROM Cart c WHERE c.menu.id = :menuId")
    Long findMaxCartIdByMenuId(@Param("menuId") Long menuId);

    @Modifying
    @Query("DELETE FROM Cart c WHERE c.id = :cartId")
    void deleteCartMenu(@Param("cartId") Long cartId);
}
