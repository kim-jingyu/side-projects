package com.myproject.todayhouse.item.repository;

import com.myproject.todayhouse.item.domain.Item;
import com.myproject.todayhouse.item.domain.ItemSellStatus;
import com.myproject.todayhouse.item.dto.request.ItemAdminSearchRequest;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

import static com.myproject.todayhouse.item.domain.QItem.item;

public class ItemRepositoryImpl implements ItemRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public ItemRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Item> getAdminItemPage(ItemAdminSearchRequest request, Pageable pageable) {
        List<Item> content = queryFactory
                .selectFrom(item)
                .where(itemNameLike(request.getItemName()),
                        createdDateAfter(request.getCreatedDate()),
                        itemSellStatusEq(request.getItemSellStatus()))
                .orderBy(item.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(content, pageable, content.size());
    }
    private BooleanExpression itemNameLike(String itemName) {
        return StringUtils.hasText(itemName) ? item.itemName.like("%" + itemName + "%") : null;
    }

    private BooleanExpression createdDateAfter(String createdDate) {
        if (!StringUtils.hasText(createdDate)) {
            return null;
        }

        LocalDateTime dateTime = LocalDateTime.now();

        switch (createdDate) {
            case "1d" -> dateTime = dateTime.minusDays(1);
            case "1w" -> dateTime = dateTime.minusWeeks(1);
            case "1m" -> dateTime = dateTime.minusMonths(1);
            case "6m" -> dateTime = dateTime.minusMonths(6);
        }

        return item.createdAt.after(dateTime);
    }

    private BooleanExpression itemSellStatusEq(ItemSellStatus itemSellStatus) {
        return itemSellStatus != null ? item.itemSellStatus.eq(itemSellStatus) : null;
    }

}
