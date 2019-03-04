package com.itashiev.merger.models;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Id;
import java.util.List;

@Data
@Builder
public class Lead {

    @Id
    private Long id;

    private String name;

    private List<Pooling> poolings;
}
