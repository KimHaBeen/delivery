package kopo.delivery.controller;

import jakarta.servlet.http.HttpSession;
import kopo.delivery.entity.Category;
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
import java.util.Optional;


@RequiredArgsConstructor
@Controller
public class StoreController {

	private final MenuService menuService;
	private final MainService mainService;

	@GetMapping("/category/{categoryID}")
	public String menuPage(@PathVariable("categoryID") Long categoryID, Model model, HttpSession session) {
		//header 주소설정
		String address = (String) session.getAttribute("selectAddress");
		String selectAddress = (address != null)
				? mainService.sessionValue(address)
				: mainService.roleAddress();

		model.addAttribute("selectAddress", selectAddress);

		//로그인조회
		String userID = (String) session.getAttribute("userID");
		System.out.println(userID);
		model.addAttribute("userID", userID);

		if (userID != null) {
			System.out.println(userID);
		}else {
			System.out.println("없음");
		}

		// 1. 카테고리 정보 조회 (항상 존재)
		Optional<Category> category = menuService.getCategoryById(categoryID);
		String category_name = category.map(Category::getCategory).orElse("null");
		model.addAttribute("category", category_name);
		System.out.println(category_name);

		//카테고리에 맞는 가게 조회
		List<Store> stores = menuService.getStoreByCategory(Math.toIntExact(categoryID));
		model.addAttribute("stores", stores);

		//메뉴그룹화
		Map<Store, List<StoreMenu>> groupMenus = new HashMap<>();
		for (Store store : stores) {
			List<StoreMenu> menus = menuService.getMenuByStore(store.getStoreID());
			groupMenus.put(store, menus);
		}
		model.addAttribute("groupMenus", groupMenus);

		return "menu/store";
	}
	

}
