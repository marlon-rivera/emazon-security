package com.emazon.security.configuration;

import com.emazon.security.adapters.driven.encoder.EncoderAdapter;
import com.emazon.security.adapters.driven.jpa.mysql.adapter.UserAdapter;
import com.emazon.security.adapters.driven.jpa.mysql.mapper.IUserEntityMapper;
import com.emazon.security.adapters.driven.jpa.mysql.repository.IUserRepository;
import com.emazon.security.adapters.driven.jwt.JwtAdapter;
import com.emazon.security.configuration.jwt.JwtService;
import com.emazon.security.domain.api.IUserServicePort;
import com.emazon.security.domain.api.usecase.UserUseCaseImpl;
import com.emazon.security.domain.spi.IEncoderPort;
import com.emazon.security.domain.spi.IJwtPort;
import com.emazon.security.domain.spi.IUserPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;


    @Bean
    public IEncoderPort encoderPort() {
        return new EncoderAdapter(passwordEncoder);
    }

    @Bean IJwtPort jwtPort() {
        return new JwtAdapter(jwtService);
    }

    @Bean
    public IUserPersistencePort persistencePort(){
        return new UserAdapter(userRepository, userEntityMapper, jwtPort());
    }

    @Bean
    public IUserServicePort userServicePort() {return new UserUseCaseImpl(persistencePort(), encoderPort());}

}
