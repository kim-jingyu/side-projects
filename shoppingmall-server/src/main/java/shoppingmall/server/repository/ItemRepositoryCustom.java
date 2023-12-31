package shoppingmall.server.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shoppingmall.server.dto.ItemSearchDto;
import shoppingmall.server.dto.MainPageItemDto;
import shoppingmall.server.entity.Item;

public interface ItemRepositoryCustom {
    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
    Page<MainPageItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
}
