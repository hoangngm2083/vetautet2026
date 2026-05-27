package ngm.hoang.utils.hasher;

import lombok.RequiredArgsConstructor;
import ngm.hoang.provider.Hasher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpringSecurityHasherAdapter implements Hasher {

    private final PasswordEncoder springPasswordEncoder;

    @Override
    public String hash(String rawPassword) {
        return springPasswordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return springPasswordEncoder.matches(rawPassword, encodedPassword);
    }
}
