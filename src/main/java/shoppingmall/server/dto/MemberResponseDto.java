package shoppingmall.server.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class MemberResponseDto {
    private String memberName;
}
