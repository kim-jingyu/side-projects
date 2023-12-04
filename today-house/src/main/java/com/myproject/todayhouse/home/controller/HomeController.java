package com.myproject.todayhouse.home.controller;

import com.myproject.todayhouse.home.service.HomeService;
import com.myproject.todayhouse.item.domain.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeController {
    private final HomeService homeService;

    @GetMapping("/")
    public ResponseEntity getHomePage(@RequestParam String itemName) {
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "itemName"));

        Page<Item> items = homeService.getHomeItemPage(itemName, pageRequest);

        return ResponseEntity
                .ok()
                .body(items);
    }
}
