package com.example.demo.service;

import com.example.demo.model.entity.DayEntity;
import com.example.demo.model.entity.MenuEntity;
import com.example.demo.repository.DayRepository;
import com.example.demo.repository.MenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DayService {
    private final DayRepository dayRepository;
    private final MenuRepository menuRepository;

    public DayService(DayRepository dayRepository, MenuRepository menuRepository) {
        this.dayRepository = dayRepository;
        this.menuRepository = menuRepository;
    }

    public void saveSelectedMenu(String date, String menuName) {
        MenuEntity menu = menuRepository.findByMenuName(menuName);

        // 同じ日付のデータがすでに存在するかチェックする
        DayEntity dayEntity = dayRepository.findAll().stream()
                .filter(d -> date.equals(d.getDate())) // ★ここを d.getDate() に修正
                .findFirst()
                .orElse(null);

        if (dayEntity == null) {
            // 新規作成
            dayEntity = new DayEntity();
            dayEntity.setDayId(UUID.randomUUID().toString());
            dayEntity.setCreatedAt(LocalDateTime.now());
        }

        // データの更新
        dayEntity.setMenuId(menu != null ? menu.getMenuId() : null);
        dayEntity.setMenuName(menuName);
        dayEntity.setDate(date); // 文字列のままセット
        dayEntity.setUpdatedAt(LocalDateTime.now());

        dayRepository.save(dayEntity);
    }

    public List<DayEntity> findAllDays() {
        List<DayEntity> days = dayRepository.findAll();

        return days.stream()
                .filter(day -> day != null && day.getDate() != null)
                .collect(Collectors.toList());
    }

    @Transactional
    public void saveMonthlyMenus(List<DayEntity> scheduleList) {
        for (DayEntity schedule : scheduleList) {

            saveSelectedMenu(schedule.getDate(), schedule.getMenuName());
        }
    }
}