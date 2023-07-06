package shoppingmall.server.controller;

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
    public ResponseEntity<List<ItemResponseDto>> addItem(@Validated ItemRequestDto itemRequestDto, BindingResult bindingResult, @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        log.info("{}", itemRequestDto);

        List<ItemResponseDto> itemResponseDtoList = new ArrayList<>();

        try {
            itemResponseDtoList.add(itemService.saveItem(itemRequestDto, itemImgFileList));
        } catch (IOException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        return ResponseEntity
                .ok()
                .body(itemResponseDtoList);
    }
}
