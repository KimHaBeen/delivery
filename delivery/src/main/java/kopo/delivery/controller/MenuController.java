package kopo.delivery.controller;

import com.sun.tools.javac.Main;
import jakarta.servlet.http.HttpSession;
import kopo.delivery.entity.StoreMenu;
import kopo.delivery.service.MainService;
import kopo.delivery.service.MenuService;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
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

	@GetMapping("/menu/jjigae")
	public String jjigaePage(Model model, HttpSession session) {

		Object addressObj = session.getAttribute("selectAddress");
		String selectAddress = null;

		if (addressObj != null) {
			selectAddress = mainService.sessionValue(addressObj);
		} else {
			selectAddress = mainService.roleAddress();
		}

		model.addAttribute("selectAddress", selectAddress);

		Map<Long, List<StoreMenu>> groupMenus = menuService.getMenuGroupByStore();

		model.addAttribute("groupMenus", groupMenus);
		System.out.println(groupMenus);

		return "menu/jjigae";
	}
	

}
