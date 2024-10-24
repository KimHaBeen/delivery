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

        Object addressObj = session.getAttribute("selectAddress");
        String selectAddress = null;

        if (addressObj != null) {
            selectAddress = mainService.sessionValue(addressObj);
        } else {
            selectAddress = mainService.roleAddress();
        }

        model.addAttribute("selectAddress", selectAddress);

        Store store = menuService.StoreID(storeId);

        model.addAttribute("store", store);
        model.addAttribute("menus", store.getMenus());
        return "menu/detailMenu";
    }

}
