package com.myproject.todayhouse.home.service;

import com.myproject.todayhouse.item.domain.Item;
import com.myproject.todayhouse.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HomeService {
    private final ItemRepository itemRepository;

    public Page<Item> getHomeItemPage(String itemName, Pageable pageable) {
        return itemRepository.findByItemName(itemName, pageable);
    }
}
