package com.atmostadam.cats.framework.model.petfinder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Contact {
    private String email;
    private String phone;
    private Address address;
}
