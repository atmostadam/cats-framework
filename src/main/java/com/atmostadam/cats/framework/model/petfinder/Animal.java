package com.atmostadam.cats.framework.model.petfinder;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Animal {
    private int id;
    private String organization_id;
    private String url;
    private String type;
    private String species;
    private Breed breeds;
    private Color colors;
    private String age;
    private String gender;
    private String size;
    private String coat;
    private Attribute attributes;
    private Environment environment;
    private List<String> tags;
    private String name;
    private String description;
    private List<Photo> photos;
    private String status;
    private String published_at;
    private Contact contact;
    private Links _links;
}
