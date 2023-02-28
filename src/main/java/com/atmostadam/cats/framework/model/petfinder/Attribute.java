package com.atmostadam.cats.framework.model.petfinder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Attribute {
    private boolean spayed_neutered;
    private boolean house_trained;
    private boolean declawed;
    private boolean special_needs;
    private boolean shots_current;
}
