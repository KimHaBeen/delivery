package kopo.delivery.serviceimpl;

import kopo.delivery.entity.Store;
import kopo.delivery.entity.StoreMenu;
import kopo.delivery.repository.StoreMenuRepo;
import kopo.delivery.repository.StoreRepo;
import kopo.delivery.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MenuServiceImpl implements MenuService {

    private final StoreMenuRepo storeMenuRepo;
    private final StoreRepo storeRepo;

    @Override
    public Map<Long, List<StoreMenu>> getMenuGroupByStore() {

        List<StoreMenu> allMenu = storeMenuRepo.findAll();
        return allMenu.stream()
                .collect(Collectors.groupingBy(menu -> menu.getStore().getStoreID()));
    }

    @Override
    public Store StoreID(Long storeID) {
        return storeRepo.findById(storeID)
                .orElseThrow(() -> new NoSuchElementException("Store ID not found: " + storeID));
    }

    @Override
    public List<Store> getStoreByCategory(int categoryID) {
        return storeRepo.findByCategory_Id(categoryID);
    }

    @Override
    public List<StoreMenu> getMenuByStore(Long storeId) {
        return storeMenuRepo.findByStore_storeID(storeId);
    }
}
