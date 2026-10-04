package com.albinos.ordering.infrastructure.persistence.provider;

import com.albinos.ordering.domain.model.entity.Order;
import com.albinos.ordering.domain.model.repository.Orders;
import com.albinos.ordering.domain.model.valueobject.id.OrderId;
import com.albinos.ordering.infrastructure.persistence.assembler.OrderPersistenceEntityAssembler;
import com.albinos.ordering.infrastructure.persistence.disassembler.OrderPersistenceEntityDisassembler;
import com.albinos.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import com.albinos.ordering.infrastructure.persistence.repository.OrderPersistenceEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrdersPersistenceProvider implements Orders{

    private final OrderPersistenceEntityRepository persistenceRepository;
    private final OrderPersistenceEntityAssembler assembler;
    private final OrderPersistenceEntityDisassembler disassembler;

    @Override
    public Optional<Order> ofId(OrderId orderId) {
        Optional<OrderPersistenceEntity> possibleEntity = persistenceRepository.findById
                (orderId.valeu().toLong());
        return possibleEntity.map(disassembler::toDomainEntity);
    }

    @Override
    public boolean exists(OrderId orderId) {
        return false;
    }

    @Override
    public void add(Order aggregateRoot) {
        long orderId = aggregateRoot.id().valeu().toLong();
        persistenceRepository.findById(orderId)
                .ifPresentOrElse(
                        (persistenceEntity) -> {
                            update(aggregateRoot, persistenceEntity);
                        },
                        () ->{
                            insert(aggregateRoot);
                        }
                );

    }

    private void insert(Order aggregateRoot){
        OrderPersistenceEntity persistenceEntity = assembler.fromDomain(aggregateRoot);
        persistenceRepository.saveAndFlush(persistenceEntity);
    }

    private void update(Order aggregateRoot, OrderPersistenceEntity persistenceEntity){
        persistenceEntity = assembler.merge(persistenceEntity, aggregateRoot);
        persistenceRepository.saveAndFlush(persistenceEntity);
    }

    @Override
    public int count() {
        return 0;
    }
}
