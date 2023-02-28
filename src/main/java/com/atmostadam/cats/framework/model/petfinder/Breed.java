package com.atmostadam.cats.framework.model.petfinder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Breed {
    private String primary;
    private String secondary;
    private boolean mixed;
    private boolean unknown;
}
