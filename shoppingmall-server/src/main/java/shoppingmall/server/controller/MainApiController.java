package shoppingmall.server.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import shoppingmall.server.dto.ItemSearchDto;
import shoppingmall.server.dto.MainPageItemDto;
import shoppingmall.server.service.ItemService;

import java.util.Optional;

@Tag(name = "메인 페이지", description = "등록된 상품 정보 목록")
@RestController
@RequiredArgsConstructor
public class MainApiController {
    private final ItemService itemService;

    @Operation(summary = "메인 페이지")
    @GetMapping("/")
    public ResponseEntity getMainPage(ItemSearchDto itemSearchDto, Optional<Integer> page) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);

        Page<MainPageItemDto> mainPageItems = itemService.getMainItemPage(itemSearchDto, pageable);

        return ResponseEntity
                .ok()
                .body(mainPageItems);
    }
}
