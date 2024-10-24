package kopo.delivery.service;

import kopo.delivery.dto.CartDTO;
import kopo.delivery.entity.Store;
import kopo.delivery.entity.StoreMenu;

import java.util.List;
import java.util.Map;

public interface MenuService {

     Map<Long, List<StoreMenu>> getMenuGroupByStore();

     Store StoreID(Long storeID);

     List<Store> getStoreByCategory(int categoryID);

     List<StoreMenu> getMenuByStore(Long storeId);

}
