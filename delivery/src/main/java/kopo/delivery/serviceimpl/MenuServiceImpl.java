package kopo.delivery.serviceimpl;

import kopo.delivery.entity.StoreMenu;
import kopo.delivery.repository.StoreMenuRepo;
import kopo.delivery.repository.StoreRepo;
import kopo.delivery.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class MenuServiceImpl implements MenuService {

    private final StoreMenuRepo storeMenuRepo;
    private final StoreRepo storeRepo;

    @Override
    public List<StoreMenu> getAllMenuList() {
        return storeMenuRepo.findAll();
    }
}
