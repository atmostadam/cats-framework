package com.atmostadam.cats.framework.model.adoptapet;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(fluent = true, chain = true)
public class LimitedPetImage {
    private String thumbnail_url;

    private Integer thumbnail_width;

    private Integer thumbnail_height;

    private String original_url;

    private Integer original_width;

    private Integer original_height;
}
