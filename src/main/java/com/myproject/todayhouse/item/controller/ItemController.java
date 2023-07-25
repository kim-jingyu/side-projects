package com.myproject.todayhouse.item.controller;

import com.myproject.todayhouse.item.dto.request.ItemCreateRequest;
import com.myproject.todayhouse.item.dto.request.ItemUpdateRequest;
import com.myproject.todayhouse.item.dto.response.ItemResponse;
import com.myproject.todayhouse.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping("/admin/item")
    public ResponseEntity addItem(@Validated @ModelAttribute ItemCreateRequest itemCreateRequest, @RequestParam("itemImgFileList") List<MultipartFile> itemImgFileList, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .badRequest()
                    .body(bindingResult);
        }

        ItemResponse itemResponse = itemService.saveItem(itemCreateRequest, itemImgFileList);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(itemResponse);
    }

    @PutMapping("/admin/item/{itemId}")
    public ResponseEntity updateItem(@PathVariable("itemId") Long itemId, @Validated @ModelAttribute ItemUpdateRequest itemUpdateRequest, @RequestParam("itemImgFileList") List<MultipartFile> itemImgFileList, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .badRequest()
                    .body(bindingResult);
        }

        ItemResponse itemResponse = itemService.updateItem(itemId, itemUpdateRequest, itemImgFileList);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(itemResponse);
    }
}
