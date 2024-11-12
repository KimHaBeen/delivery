package kopo.delivery.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;
import kopo.delivery.dto.CartDTO;
import kopo.delivery.entity.Store;
import kopo.delivery.service.MainService;
import kopo.delivery.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Controller
public class DetailMenuController {

    final MenuService menuService;
    final MainService mainService;

    @GetMapping("/store/{storeId}")
    public String detailMenu(@PathVariable("storeId") Long storeId, Model model, HttpSession session) {

        /*String roleYaddress = mainService.roleAddress();
        String selectAddress = (String) session.getAttribute("selectAddress");

        if (selectAddress == null || selectAddress.isEmpty()) {
            selectAddress = roleYaddress;
        }
        model.addAttribute("selectAddress", selectAddress);*/

        String address = (String) session.getAttribute("selectAddress");
        System.out.println("세션에 저장되어 있는 애는 누구인가?" + address);
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

        Store store = menuService.StoreID(storeId);

        model.addAttribute("store", store);
        model.addAttribute("menus", store.getMenus());
        return "menu/detailMenu";
    }

}
