package com.emazon.security.adapters.driven.encoder;

import com.emazon.security.domain.spi.IEncoderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class EncoderAdapter implements IEncoderPort {

    private final PasswordEncoder passwordEncoder;

    @Override
    public String encode(String text) {
        return passwordEncoder.encode(text);
    }

    @Override
    public boolean matches(String text) {
        return false;
    }
}
