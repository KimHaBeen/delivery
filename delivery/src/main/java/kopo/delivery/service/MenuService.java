package kopo.delivery.service;

import kopo.delivery.entity.Category;
import kopo.delivery.entity.Store;
import kopo.delivery.entity.StoreMenu;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MenuService {

     Map<Long, List<StoreMenu>> getMenuGroupByStore();

     Store StoreID(Long storeID);

     List<Store> getStoreByCategory(int categoryID);

     List<StoreMenu> getMenuByStore(Long storeId);

     Optional<Category> getCategoryById(Long CategoryID);
}
