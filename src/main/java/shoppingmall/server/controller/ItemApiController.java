package shoppingmall.server.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shoppingmall.server.dto.ItemRequestDto;
import shoppingmall.server.dto.ItemResponseDto;
import shoppingmall.server.dto.ItemSearchDto;
import shoppingmall.server.entity.Item;
import shoppingmall.server.service.ItemService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Tag(name = "상품 관리 페이지", description = "관리자가 상품을 관리하는 페이지입니다.")
@RestController
@RequiredArgsConstructor
@Slf4j
public class ItemApiController {
    private final ItemService itemService;

    // 상품 등록
    @Operation(summary = "상품 등록")
    @PostMapping(value = "/admin/item")
    public ResponseEntity addItem(@Validated ItemRequestDto itemRequestDto, BindingResult bindingResult, @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList) {
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
    @Operation(summary = "상품 상세정보")
    @GetMapping(value = "/admin/item/{itemId}")
    public ResponseEntity getItemDetail(@PathVariable Long itemId) {
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
    @Operation(summary = "상품 수정")
    @PutMapping(value = "/admin/item/{itemId}")
    public ResponseEntity updateItem(@PathVariable Long itemId, @Validated @ModelAttribute ItemRequestDto itemRequestDto, BindingResult bindingResult, @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList) {
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

    // 상품 리스트 조회
    @Operation(summary = "상품 목록")
    @GetMapping(value = {"/admin/items", "/admin/items/{page}"})
    public ResponseEntity getItemList(ItemSearchDto itemSearchDto, @PathVariable("page") Optional<Integer> page) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);

        Page<Item> adminItemPage;
        try {
            adminItemPage = itemService.getAdminItemPage(itemSearchDto, pageable);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }

        return ResponseEntity
                .ok()
                .body(adminItemPage);
    }
}