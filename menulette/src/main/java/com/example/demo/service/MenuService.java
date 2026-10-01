package com.example.demo.service;

import com.example.demo.model.entity.MenuEntity;
import com.example.demo.repository.MenuRepository;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;
import java.util.UUID;


@Service
public class MenuService {
    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public  List<MenuEntity> findAll() {
        return menuRepository.findAll();
    }

    public void saveMenu(String menuName, String category) {
        MenuEntity menu = new MenuEntity();
        menu.setMenuId(UUID.randomUUID().toString());
        menu.setMenuName(menuName);
        menu.setCategory(category);
        menuRepository.save(menu);
    }

    public void deleteMenu(String menuId) {
        menuRepository.deleteById(menuId);
    }

}

