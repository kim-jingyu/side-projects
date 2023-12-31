package shoppingmall.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shoppingmall.server.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
}
