package com.atmostadam.cats.framework.model.adoptapet;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(fluent = true, chain = true)
public class AdoptAPetRequest {
    private String action;

    private LimitedPetDetails pet;
}
