package shoppingmall.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shoppingmall.server.entity.ItemImg;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {
}
