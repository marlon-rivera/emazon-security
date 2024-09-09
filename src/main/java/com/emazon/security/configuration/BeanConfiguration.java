package com.emazon.security.configuration;

import com.emazon.security.adapters.driven.encoder.EncoderAdapter;
import com.emazon.security.adapters.driven.jpa.mysql.adapter.UserAdapter;
import com.emazon.security.adapters.driven.jpa.mysql.mapper.IUserEntityMapper;
import com.emazon.security.adapters.driven.jpa.mysql.repository.IUserRepository;
import com.emazon.security.adapters.driven.jwt.AuthenticationAdapter;
import com.emazon.security.adapters.driven.jwt.JwtAdapter;
import com.emazon.security.configuration.jwt.JwtService;
import com.emazon.security.domain.api.IUserServicePort;
import com.emazon.security.domain.api.usecase.UserUseCaseImpl;
import com.emazon.security.domain.spi.IAuthenticationPort;
import com.emazon.security.domain.spi.IEncoderPort;
import com.emazon.security.domain.spi.IJwtPort;
import com.emazon.security.domain.spi.IUserPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    @Bean
    public IEncoderPort encoderPort() {
        return new EncoderAdapter(passwordEncoder);
    }

    @Bean
    public IJwtPort jwtPort() {
        return new JwtAdapter(jwtService);
    }

    @Bean
    public IAuthenticationPort authenticationPort() {
        return new AuthenticationAdapter(authenticationManager);
    }

    @Bean
    public IUserPersistencePort persistencePort(){
        return new UserAdapter(userRepository, userEntityMapper, jwtPort(), authenticationPort());
    }

    @Bean
    public IUserServicePort userServicePort() {return new UserUseCaseImpl(persistencePort(), encoderPort());}

}
