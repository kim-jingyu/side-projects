package com.myproject.todayhouse.item.service;

import com.myproject.todayhouse.item.repository.ItemImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemImgService {
    private ItemImgRepository itemImgRepository;

    @Transactional
    public void saveItemImg() {
    }
}
