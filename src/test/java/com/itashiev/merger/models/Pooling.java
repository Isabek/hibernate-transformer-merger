package com.itashiev.merger.models;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Id;
import java.util.List;

@Data
@Builder
public class Pooling {

    @Id
    private Long id;

    private String name;

    private List<PoolingTract> poolingTracts;
}
