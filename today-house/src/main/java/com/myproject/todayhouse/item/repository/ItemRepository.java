package com.myproject.todayhouse.item.repository;

import com.myproject.todayhouse.item.domain.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom {
    Optional<Item> findByItemNameAndBrandName(String itemName, String BrandName);
    Page<Item> findByItemName(String itemName, Pageable pageable);
}
