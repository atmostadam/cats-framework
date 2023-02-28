package com.atmostadam.cats.framework.model.petfinder;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PetfinderRequest {
    List<Animal> animals = new ArrayList<>();
}
