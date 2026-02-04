package org.example.configuration;

import org.example.adapters.RolJpaAdapter;
import org.example.adapters.UsuarioJpaAdapter;
import org.exemple.ports.api.RoleServicePort;
import org.exemple.ports.api.UserServicePort;
import org.exemple.ports.spi.RolPersistencePort;
import org.exemple.ports.spi.UserPersistencePort;
import org.exemple.service.RoleServiceImpl;
import org.exemple.service.UsuarioServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthConfig {
    @Bean
    public RolPersistencePort rolPersistencePort(){
        return new RolJpaAdapter();
    }
    @Bean
    public RoleServicePort roleServicePort(){
        return new RoleServiceImpl(rolPersistencePort());
    }

    @Bean
    public UserPersistencePort userPersistencePort(){
        return new UsuarioJpaAdapter();
    }
    @Bean
    public UserServicePort userServicePort(){
        return new UsuarioServiceImpl(userPersistencePort());
    }
}
