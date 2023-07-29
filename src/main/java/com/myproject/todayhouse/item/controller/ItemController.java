package com.myproject.todayhouse.item.controller;

import com.myproject.todayhouse.item.domain.Item;
import com.myproject.todayhouse.item.dto.request.ItemAdminSearchRequest;
import com.myproject.todayhouse.item.dto.request.ItemCreateRequest;
import com.myproject.todayhouse.item.dto.request.ItemUpdateRequest;
import com.myproject.todayhouse.item.dto.response.ItemResponse;
import com.myproject.todayhouse.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static com.myproject.todayhouse.item.domain.QItem.item;

@RestController
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping("/admin/item")
    public ResponseEntity addItem(@Validated @ModelAttribute ItemCreateRequest itemCreateRequest, BindingResult bindingResult, @RequestParam("itemImgFileList") List<MultipartFile> itemImgFileList) {
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
    public ResponseEntity updateItem(@PathVariable("itemId") Long itemId, ItemUpdateRequest itemUpdateRequest, BindingResult bindingResult, @RequestParam("itemImgFileList") List<MultipartFile> itemImgFileList) {
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

    @GetMapping(value = {"/admin/items", "/admin/items/{page}"})
    public ResponseEntity getAdminList(@ModelAttribute ItemAdminSearchRequest request, @PathVariable("page") Optional<Integer> page) {
        PageRequest pageRequest = PageRequest.of(page.isPresent() ? page.get() : 0, 3);

        Page<Item> adminItemPage = itemService.getAdminItemPage(request, pageRequest);
        return ResponseEntity
                .ok()
                .body(adminItemPage);
    }
}
