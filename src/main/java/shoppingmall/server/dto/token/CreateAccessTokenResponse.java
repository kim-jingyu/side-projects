package shoppingmall.server.dto.token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class CreateAccessTokenResponse {
    private String accessToken;
}
