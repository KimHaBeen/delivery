package kopo.delivery.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kopo.delivery.dto.UserDTO;
import kopo.delivery.entity.Category;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import kopo.delivery.dto.AddressDTO;
import kopo.delivery.entity.Address;
import kopo.delivery.service.MainService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class HomeController {
	
	private final MainService mainService;

	@GetMapping("/")
	public String home(Model model, HttpSession session) {
		
		/*String roleYaddress = mainservice.roleAddress();
		model.addAttribute("address", roleYaddress);
		
		String selectAddress = (String) session.getAttribute("selectAddress");*/

		String address = (String) session.getAttribute("selectAddress");
		System.out.println("세션에 저장되어 있는 애는 누구인가?" + address);
		String selectAddress = (address != null)
				? mainService.sessionValue(address)
				: mainService.roleAddress();

		model.addAttribute("selectAddress", selectAddress);


		List<Category> category = mainService.getAllCategory();
		model.addAttribute("category", category);
		System.out.println(category);

		@SuppressWarnings("unchecked")
		List<UserDTO> loginuser = (List<UserDTO>) session.getAttribute("loginuser");
		model.addAttribute("loginuser", loginuser);

		if (loginuser != null) {
			System.out.println(loginuser);
		}else {
			System.out.println("없음");
		}

		return "index";
	}
	
	@GetMapping("/map")
	public String map(Model model) {
		List<Address> address = mainService.getAllAddress();
		model.addAttribute("address", address);
		return "view/map";
	}
	
	@PostMapping("/address")
	@ResponseBody
	public Map<String, String> mapComplete(@RequestBody AddressDTO addressdto) throws Exception {
		mainService.addressSave(addressdto);
		
		Map<String, String> response = new HashMap<>();
		response.put("status", "success");
		return response;
	}
	
	@PostMapping("/selectAddress")
	@ResponseBody
	public Map<String, String> selectAddress(@RequestBody Map<String, String> request, HttpSession session) {
		String selectAddress = request.get("selectAddress");
		System.out.println("선택한 주소: " + selectAddress);
		
		session.setAttribute("selectAddress", selectAddress);
		
		Map<String, String> response = new HashMap<>();
		response.put("status", "success");
		return response;
	}

	
}
