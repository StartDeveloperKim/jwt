package jwt.study.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AddUSerRequest {

    private String email;
    private String password;
}
