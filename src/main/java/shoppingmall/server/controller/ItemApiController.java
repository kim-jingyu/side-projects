package shoppingmall.server.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shoppingmall.server.dto.ItemRequestDto;
import shoppingmall.server.dto.ItemResponseDto;
import shoppingmall.server.service.ItemService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ItemApiController {
    private final ItemService itemService;

    // 상품 등록
    @PostMapping(value = "/admin/item")
    public ResponseEntity<ItemResponseDto> addItem(@Validated ItemRequestDto itemRequestDto, BindingResult bindingResult, @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        ItemResponseDto itemResponseDto;
        try {
            itemResponseDto = itemService.saveItem(itemRequestDto, itemImgFileList);
        } catch (IOException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        return ResponseEntity
                .ok()
                .body(itemResponseDto);
    }

    // 상품 상세페이지
    @GetMapping(value = "/admin/item/{itemId}")
    public ResponseEntity<ItemResponseDto> getItemDetail(@PathVariable Long itemId) {
        ItemResponseDto itemResponseDto;
        try {
            itemResponseDto = itemService.getItemDetail(itemId);
        } catch (EntityNotFoundException e) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        return ResponseEntity
                .ok()
                .body(itemResponseDto);
    }

    // 상품 수정
    @PutMapping(value = "/admin/item/{itemId}")
    public ResponseEntity<ItemResponseDto> updateItem(@PathVariable Long itemId, @Validated @ModelAttribute ItemRequestDto itemRequestDto, BindingResult bindingResult, @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList) {
        ItemResponseDto itemResponseDto;
        try {
            itemResponseDto = itemService.updateItem(itemId, itemRequestDto, itemImgFileList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity
                .ok()
                .body(itemResponseDto);
    }
}