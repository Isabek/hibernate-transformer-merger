package com.itashiev.merger.models;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Id;
import java.util.Set;

@Data
@Builder
public class PoolingTract {

    @Id
    private Long id;

    private String name;

    private Set<Quartering> quarterings;
}
