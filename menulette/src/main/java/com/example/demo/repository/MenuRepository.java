package com.example.demo.repository;

import com.example.demo.model.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<MenuEntity, String> {
    MenuEntity findByMenuName(String menuName);
}
