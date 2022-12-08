package jwt.study.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginDto {

    private String username;
    private String password;
}
