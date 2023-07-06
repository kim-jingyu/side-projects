package shoppingmall.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shoppingmall.server.entity.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
