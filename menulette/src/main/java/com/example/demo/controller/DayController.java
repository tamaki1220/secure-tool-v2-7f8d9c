package com.example.demo.controller;

import com.example.demo.model.entity.DayEntity;
import com.example.demo.service.DayService;
import com.example.demo.service.MenuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/menus")
public class DayController {
    private final DayService dayService;
    private final MenuService menuService;

    public DayController(DayService dayService, MenuService menuService) {
        this.dayService = dayService;
        this.menuService = menuService;
    }

    @GetMapping("/calendar")
    public String showCalendar(Model model) {
        List<DayEntity> savedDays = dayService.findAllDays();

        // デバッグ用ログ
        System.out.println("=== [DEBUG] 取得したsavedDaysの件数: " + savedDays.size() + " ===");
        for (DayEntity d : savedDays) {
            System.out.println("[DEBUG] 日付: " + d.getDate() + ", メニュー: " + d.getMenuName());
        }

        // そのままリストを渡す
        model.addAttribute("savedDays", savedDays);
        model.addAttribute("menuName", menuService.findAll());

        return "calender";
    }

    @PostMapping("/select")
    @ResponseBody
    public String selectedmenu(
            @RequestParam("date") String date,
            @RequestParam("menuName") String menuName) {
        dayService.saveSelectedMenu(date, menuName);
        return "success";
    }

    @PostMapping("/save-month")
    @ResponseBody
    public String saveMonthMenus(@RequestBody List<DayEntity> scheduleList) {
        try {
            dayService.saveMonthlyMenus(scheduleList);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
}