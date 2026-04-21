package org.exemple.service;

import lombok.AllArgsConstructor;
import org.exemple.data.rest.dtos.customer.CustomerInfo;
import org.exemple.ports.api.CustomerProviderServicePort;
import org.exemple.ports.spi.CustomerProviderServicePersistencePort;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@AllArgsConstructor
public class JsonPlaceCustomerServiceImpl implements CustomerProviderServicePort {

    private final CustomerProviderServicePersistencePort customerProviderServicePersistencePort;

    @Override
    public Optional<CustomerInfo> findById(Long id) {
        Optional<CustomerInfo> customerInfo = customerProviderServicePersistencePort.findById(id);
        if (customerInfo.isPresent()) {
            return customerInfo;
        }
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }
}
