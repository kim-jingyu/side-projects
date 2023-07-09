package shoppingmall.server.dto;

import lombok.Getter;
import lombok.Setter;
import shoppingmall.server.constant.ItemSellStatus;

/**
 * 조회 조건
 * - 상품 등록일
 *  - all: 상품 등록일 전체
 *  - 1d: 최근 하루 동안 등록된 상품
 *  - 1w: 최근 일주일 동안 등록된 상품
 *  - 1m: 최근 한달 동안 등록된 상품
 *  - 6m: 최근 6개월 동안 등록된 상품
 * - 상품 판매 상태
 * - 상품명 또는 상품 등록자 아이디
 *  - itemName: 상품명 기준 조회
 *  - createdBy: 상품 등록자 아이디
 */
@Getter @Setter
public class ItemSearchDto {
    private String searchDateType;
    private ItemSellStatus itemSellStatus;
    private String searchBy;
    private String searchQuery = "";    // 조회할 검색어를 저장할 변수. itemName 일 경우에는 상품명을 기준으로 하고, createdBy 일 경우에는 상품 등록자 아이디 기준으로 검색한다.
}
