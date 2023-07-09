package shoppingmall.server.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import shoppingmall.server.constant.ItemSellStatus;
import shoppingmall.server.dto.ItemSearchDto;
import shoppingmall.server.dto.MainPageItemDto;
import shoppingmall.server.entity.Item;
import shoppingmall.server.entity.QItem;

import java.time.LocalDateTime;
import java.util.List;

public class ItemRepositoryImpl implements ItemRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public ItemRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        List<Item> content = queryFactory
                .selectFrom(QItem.item)
                .where(searchByDateAfter(itemSearchDto.getSearchDateType()),
                        searchBySellStatus(itemSearchDto.getItemSellStatus()),
                        searchByLike(itemSearchDto.getSearchBy(), itemSearchDto.getSearchQuery()))
                .orderBy(QItem.item.itemId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(content, pageable, content.size());
    }

    @Override
    public Page<MainPageItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {

        return null;
    }

    private BooleanExpression searchByDateAfter(String searchDateType) {
        if (!StringUtils.hasText(searchDateType)) return null;

        LocalDateTime dateTime = LocalDateTime.now();

        switch (searchDateType) {
            case "1d" -> dateTime = dateTime.minusDays(1);
            case "1w" -> dateTime = dateTime.minusWeeks(1);
            case "1m" -> dateTime = dateTime.minusMonths(1);
            case "6m" -> dateTime = dateTime.minusMonths(6);
            default -> {
                return null;
            }
        }

        return QItem.item.createdTime.after(dateTime);
    }

    private BooleanExpression searchBySellStatus(ItemSellStatus itemSellStatus) {
        return itemSellStatus == null ? null : QItem.item.itemSellStatus.eq(itemSellStatus);
    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery) {
        if (!StringUtils.hasText(searchBy) && StringUtils.hasText(searchQuery)) {
            throw new RuntimeException();
        }
        if (!StringUtils.hasText(searchBy) && !StringUtils.hasText(searchQuery)) return null;

        switch (searchBy) {
            case "itemName" -> {
                return QItem.item.itemName.like("%" + searchQuery + "%");
            }
            case "createdBy" -> {
                return QItem.item.createdBy.like("%" + searchQuery + "%");
            }
            default -> {
                return null;
            }
        }
    }
}
