package kopo.delivery.controller;

import kopo.delivery.entity.StoreMenu;
import kopo.delivery.service.MenuService;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Controller
public class MenuController {

	private final MenuService menuService;
	
	@GetMapping("/menu/bossam")
	public String bossamPage() {
		return "menu/bossam";
	}

	@GetMapping("/menu/jjigae")
	public String jjigaePage(Model model) {

		List<StoreMenu> storeMenuList = menuService.getAllMenuList();

		model.addAttribute("storeMenuList", storeMenuList);

		return "menu/jjigae";
	}
	

}
