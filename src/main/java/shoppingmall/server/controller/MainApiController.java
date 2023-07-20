package shoppingmall.server.controller;

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

@RestController
@RequiredArgsConstructor
public class MainApiController {
    private final ItemService itemService;

    @GetMapping("/")
    public ResponseEntity getMainPage(ItemSearchDto itemSearchDto, Optional<Integer> page) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);

        Page<MainPageItemDto> mainPageItems = itemService.getMainItemPage(itemSearchDto, pageable);

        return ResponseEntity
                .ok()
                .body(mainPageItems);
    }
}
