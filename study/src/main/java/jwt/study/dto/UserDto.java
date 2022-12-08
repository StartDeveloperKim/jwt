package jwt.study.dto;

import jwt.study.domain.User;
import lombok.*;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String username;

    private String password;

    private String nickname;

    private Set<AuthorityDto> authorityDtoSet;

    public static UserDto from(User user) {
        if (user == null) {
            return null;
        }

        return UserDto.builder()
                .username(user.getUsername())
                .nickname(user.getNickname())
                .authorityDtoSet(user.getAuthorities().stream()
                        .map(authority -> AuthorityDto.builder().authorityName(authority.getAuthorityName()).build())
                        .collect(Collectors.toSet()))
                .build();
    }
}
