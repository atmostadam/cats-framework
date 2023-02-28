package com.atmostadam.cats.framework.model.petfinder;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PetfinderResponse {
    List<Animal> animals = new ArrayList<>();
}
