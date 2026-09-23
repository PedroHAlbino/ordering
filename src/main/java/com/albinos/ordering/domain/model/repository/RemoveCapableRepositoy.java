package com.albinos.ordering.domain.model.repository;

import com.albinos.ordering.domain.model.entity.AggregateRoot;

public interface RemoveCapableRepositoy<T extends AggregateRoot<ID>, ID> extends Repository<T, ID>
{

    void remove(T t);
    void remove(ID id);
}
