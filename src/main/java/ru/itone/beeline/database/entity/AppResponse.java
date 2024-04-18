package ru.itone.beeline.database.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AppResponse {
    
    @JsonProperty("rows")
    private Integer affectedRows;
    
    @JsonProperty("error_message")
    private String error;

    @JsonProperty("error_code")
    private String code;
}
