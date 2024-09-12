package kopo.delivery.controller;

import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RequiredArgsConstructor
@Controller
public class MenuController {
	
	@GetMapping("/menu/bossam")
	public String bossamPage() {
		return "menu/bossam";
	}
	

}
