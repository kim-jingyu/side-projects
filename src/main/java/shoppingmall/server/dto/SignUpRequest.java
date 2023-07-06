package shoppingmall.server.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class SignUpRequest {
    @NotBlank
    @Length(min = 3, max = 20)
    private String memberName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Length(min = 6, max = 20)
    private String password;

    @NotBlank
    private String address;
}
