package com.bobocode.homeworks.olehhaliak.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Photo(
    @JsonProperty("img_src")
    String imageSrc
) {


}
