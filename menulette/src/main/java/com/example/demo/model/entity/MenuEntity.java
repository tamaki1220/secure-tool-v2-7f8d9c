package com.example.demo.model.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "menus")
@Getter
@Setter
public class MenuEntity {
    @Id
    @Column(name = "menu_id")
    private String menuId;

    @Column(name = "menu_name", nullable = false, unique = true)
    private String menuName;

    @Column(name = "category")
    private String category;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        if (this.menuId == null || this.menuId.isEmpty()) {
            this.menuId = UUID.randomUUID().toString();
        }
        LocalDateTime now = LocalDateTime.now();
        if (this.createdAt == null) {
            this.createdAt = now;
        }
        if (this.updatedAt == null) {
            this.updatedAt = now;
        }
    }
}
