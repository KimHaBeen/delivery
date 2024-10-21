package kopo.delivery.controller;

import jakarta.servlet.http.HttpSession;
import kopo.delivery.entity.Store;
import kopo.delivery.entity.StoreMenu;
import kopo.delivery.service.MainService;
import kopo.delivery.service.MenuService;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@Controller
public class MenuController {

	private final MenuService menuService;
	private final MainService mainService;
	
	@GetMapping("/menu/bossam")
	public String bossamPage() {
		return "menu/bossam";
	}

	@GetMapping("/menu/{categoryID}")
	public String menuPage(@PathVariable("categoryID") int categoryID, Model model, HttpSession session) {
		//header 주소설정
		Object addressObj = session.getAttribute("selectAddress");
		String selectAddress = (addressObj != null)
				? mainService.sessionValue(addressObj)
				: mainService.roleAddress();

		model.addAttribute("selectAddress", selectAddress);

		//카테고리에 맞는 가게 조회
		List<Store> stores = menuService.getStoreByCategory(categoryID);
		model.addAttribute("stores", stores);

		//메뉴그룹화
		Map<Store, List<StoreMenu>> groupMenus = new HashMap<>();
		for (Store store : stores) {
			List<StoreMenu> menus = menuService.getMenuByStore(store.getStoreID());
			groupMenus.put(store, menus);
		}
		model.addAttribute("groupMenus", groupMenus);

		return "menu/menu";
	}
	

}
