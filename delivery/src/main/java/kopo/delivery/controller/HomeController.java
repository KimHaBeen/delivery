package kopo.delivery.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kopo.delivery.dto.AddressDTO;
import kopo.delivery.entity.Address;
import kopo.delivery.service.MainService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class HomeController {
	
	private final MainService mainsevice;

	@GetMapping("/")
	public String home() {
		return "index";
	}
	
	@GetMapping("/map")
	public String map(Model model) {
		List<Address> address = mainsevice.getAllAddress();
		model.addAttribute("address", address);
		return "view/map";
	}
	
	@PostMapping("/address")
	@ResponseBody
	public Map<String, String> mapComplete(@RequestBody AddressDTO addressdto) throws Exception {
		mainsevice.addressSave(addressdto);
		
		Map<String, String> response = new HashMap<>();
		response.put("status", "success");
		return response;
	}
	
}
