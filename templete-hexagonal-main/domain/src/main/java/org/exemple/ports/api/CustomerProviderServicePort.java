package org.exemple.ports.api;

import org.exemple.data.rest.dtos.customer.CustomerInfo;

import java.util.Optional;

public interface CustomerProviderServicePort {
    Optional<CustomerInfo> findById(Long id);
    boolean existsById(Long id);
}
