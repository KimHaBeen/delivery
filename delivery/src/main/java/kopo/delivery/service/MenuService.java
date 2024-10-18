package kopo.delivery.service;

import kopo.delivery.entity.StoreMenu;

import java.util.List;
import java.util.Map;

public interface MenuService {

    public Map<Long, List<StoreMenu>> getMenuGroupByStore();
}
