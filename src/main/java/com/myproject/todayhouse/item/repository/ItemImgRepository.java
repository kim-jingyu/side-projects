package com.myproject.todayhouse.item.repository;

import com.myproject.todayhouse.item.domain.ItemImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {
    Optional<ItemImg> findByItem_ItemIdAndItemImgId(Long itemId, Long itemImgId);
}
