package shoppingmall.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shoppingmall.server.entity.ItemImg;

import java.util.List;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {
    List<ItemImg> findByItem_ItemIdOrderByItemImgIdAsc(Long itemId);
}
