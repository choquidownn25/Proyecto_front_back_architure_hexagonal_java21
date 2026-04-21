package org.exemple.ports.spi;

import org.exemple.data.rest.dtos.customer.CustomerInfo;

import java.util.Optional;

/**
 *  Port for external service for JSONPlaceholder
 */
public interface CustomerProviderServicePersistencePort {

    Optional<CustomerInfo> findById(Long id);
    boolean existsById(Long id);
}
