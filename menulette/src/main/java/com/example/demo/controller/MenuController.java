package com.example.demo.controller;

import com.example.demo.model.entity.MenuEntity;
import com.example.demo.service.DayService;
import com.example.demo.service.MenuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("/menus")
public class MenuController {
    private final MenuService menuService;
    private final DayService dayService;

    public MenuController(MenuService menuService, DayService dayService) {
        this.menuService = menuService;
        this.dayService = dayService;
    }

    @GetMapping("/add")
    public String menu(Model model) {
        model.addAttribute("menuName", menuService.findAll());
        model.addAttribute("savedDays", dayService.findAllDays());
        model.addAttribute("form", new MenuEntity());
        return "calender";
    }

    @PostMapping("/add")
    @ResponseBody
    public String addMenu(
            @RequestParam("menuName")String menuName,
            @RequestParam("category")String category) {
        menuService.saveMenu(menuName, category);

        return "success";
    }

    @PostMapping("/delete")
    @ResponseBody
    public String deleteMenu(@RequestParam("menuId") String menuId) {
        menuService.deleteMenu(menuId);
        return "success";
    }
}
