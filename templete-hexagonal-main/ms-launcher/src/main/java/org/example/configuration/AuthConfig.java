package org.example.configuration;

import org.example.adapters.JsonPlaceholderCustomerProviderAdapter;
import org.example.adapters.RolJpaAdapter;
import org.example.adapters.UsuarioJpaAdapter;
import org.exemple.data.config.JsonplaceholderConfigModel;
import org.exemple.data.rest.mappers.CustomerMapper;
import org.exemple.ports.api.CustomerProviderServicePort;
import org.exemple.ports.api.RoleServicePort;
import org.exemple.ports.api.UserServicePort;
import org.exemple.ports.spi.CustomerProviderServicePersistencePort;
import org.exemple.ports.spi.RolPersistencePort;
import org.exemple.ports.spi.UserPersistencePort;
import org.exemple.service.JsonPlaceCustomerServiceImpl;
import org.exemple.service.RoleServiceImpl;
import org.exemple.service.UsuarioServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class AuthConfig {
    public AuthConfig(RestClient jsonClient, CustomerMapper customerMapper, JsonplaceholderConfigModel jsonConfig) {
        this.jsonClient = jsonClient;
        this.customerMapper = customerMapper;
        this.jsonConfig = jsonConfig;
    }

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

    @Bean
    public CustomerProviderServicePort customerProviderServicePort(){
        return new JsonPlaceCustomerServiceImpl(customerProviderServicePersistencePort());
    }
    private final RestClient jsonClient;
    private final CustomerMapper customerMapper;

    private final JsonplaceholderConfigModel jsonConfig;
    @Bean
    public CustomerProviderServicePersistencePort customerProviderServicePersistencePort(){
        return new JsonPlaceholderCustomerProviderAdapter(jsonClient, customerMapper, jsonConfig);

    }
}
