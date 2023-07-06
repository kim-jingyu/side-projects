package shoppingmall.server.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class LogInRequest {
    @NotBlank
    @Email
    @Length(min = 3, max = 20)
    private String email;
    private String password;
}
