package com.atmostadam.cats.framework.model.adoptapet;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdoptAPetResponse {
    private String status;

    private LimitedPetDetails pet;
}
