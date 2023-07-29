package com.myproject.todayhouse.item.repository;

import com.myproject.todayhouse.item.domain.ItemImg;
import com.myproject.todayhouse.item.dto.response.ItemImgResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {
    Optional<ItemImg> findByItem_ItemIdAndItemImgId(Long itemId, Long itemImgId);

    @Query("select new com.myproject.todayhouse.item.dto.response.ItemResponse(ig.itemImgId, ig.uploadFileName, ig.thumbFileName, ig.thumbFileUrl, ig.storedFileName, ig.storedFileUrl, ig.representYn) from ItemImg ig join ig.item i where i.itemId = :itemId")
    List<ItemImgResponse> findItemImgResponse(Long itemId);
}
