package com.myproject.todayhouse.item.repository;

import com.myproject.todayhouse.item.domain.Item;
import com.myproject.todayhouse.item.dto.request.ItemAdminSearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {
    Page<Item> getAdminItemPage(ItemAdminSearchRequest itemAdminSearchRequest, Pageable pageable);
}
