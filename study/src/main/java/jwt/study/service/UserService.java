package jwt.study.service;

import jwt.study.domain.User;
import jwt.study.dto.AddUSerRequest;
import jwt.study.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long save(final AddUSerRequest addUSerRequest) {
        return userRepository.save(User.builder()
                .email(addUSerRequest.getEmail())
                .password(bCryptPasswordEncoder.encode(addUSerRequest.getPassword()))
                .build()).getId();
    }
}
