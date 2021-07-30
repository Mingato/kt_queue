package com.abinbev.producer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@JsonSerialize
public class Message {

    @JsonProperty
    private String text;

    @JsonProperty
    private String from;

    @JsonProperty
    private Long timestamp;
}
