package kopo.delivery;

import jakarta.transaction.Transactional;
import kopo.delivery.entity.Store;
import kopo.delivery.entity.StoreMenu;
import kopo.delivery.repository.StoreMenuRepo;
import kopo.delivery.repository.StoreRepo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

@SpringBootTest
@Transactional
public class StoreMenuTest {

    @Autowired
    private StoreRepo storeRepo;

    @Autowired
    private StoreMenuRepo storeMenuRepo;

    @Test
    @DisplayName("store테이블과 Menu테이블 조인")
    public void test() {
        // 1. Store 엔티티 생성 및 저장
        Store store = new Store();
        store.setStoreID(1L);
        store.setStoreName("Sample Store");
        store.setStoreMinAmount(5000);
        store.setCategory("Fast Food");
        storeRepo.save(store);

        // 2. StoreMenu 엔티티 생성 및 저장
        StoreMenu menu1 = new StoreMenu();
        menu1.setMenuName("Burger");
        menu1.setMenuAmount(10);
        menu1.setUrl("http://example.com/burger");
        menu1.setStore(store);
        storeMenuRepo.save(menu1);

        StoreMenu menu2 = new StoreMenu();
        menu2.setMenuName("Pizza");
        menu2.setMenuAmount(20);
        menu2.setUrl("http://example.com/pizza");
        menu2.setStore(store);
        storeMenuRepo.save(menu2);

        // 3. Store ID로 StoreMenu 조회
        List<StoreMenu> storeMenus = storeMenuRepo.findByStore_storeID(1L);

        // 4. 테스트 결과 검증
        assertThat(storeMenus).hasSize(2);
        assertThat(storeMenus.get(0).getMenuName()).isEqualTo("Burger");
        assertThat(storeMenus.get(1).getMenuName()).isEqualTo("Pizza");

        // 출력 예시
        storeMenus.forEach(menu -> {
            System.out.println("Store Name: " + menu.getStore().getStoreName());
            System.out.println("Menu Name: " + menu.getMenuName());
            System.out.println("Menu Amount: " + menu.getMenuAmount());
            System.out.println("URL: " + menu.getUrl());
        });
    }
}
