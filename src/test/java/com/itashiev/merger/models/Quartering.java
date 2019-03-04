package com.itashiev.merger.models;


import lombok.Builder;
import lombok.Data;

import javax.persistence.Id;

@Data
@Builder
public class Quartering {

    @Id
    private Long id;

    private String name;
}
