package com.rest.webservices.restfulwebservices.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
//@JsonIgnoreProperties({"field1","field3"})
@JsonFilter(value = "SomeBeanFilter")
public class SomeBean {
    private String field1;
//    @JsonIgnore
    private String field2;
    private String field3;
}
