package com.myproject.todayhouse.item.service;

import com.myproject.todayhouse.item.domain.Item;
import com.myproject.todayhouse.item.domain.ItemSellStatus;
import com.myproject.todayhouse.item.dto.request.ItemRequest;
import com.myproject.todayhouse.item.dto.response.ItemResponse;
import com.myproject.todayhouse.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    @Transactional
    public ItemResponse saveItem(ItemRequest itemRequest) {
        Optional<Item> findItem = itemRepository.findByItemNameAndBrandName(itemRequest.getItemName(), itemRequest.getBrandName());
        if (findItem.isPresent()) {
            Item item = findItem.get();
            item.updateItem(itemRequest);
            return ItemResponse.builder()
                    .item(item)
                    .build();
        }

        Item savedItem = itemRepository.save(Item.builder()
                .itemRequest(itemRequest)
                .itemSellStatus(ItemSellStatus.SELL)
                .build());

        return ItemResponse.builder()
                .item(savedItem)
                .build();
    }
}
