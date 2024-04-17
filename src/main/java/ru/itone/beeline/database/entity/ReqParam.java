package ru.itone.beeline.database.entity;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ReqParam {
    
    @JsonProperty("from")
    private String from;
    
    @JsonProperty("to")
    private String to;
    
    @JsonProperty("count")
    private Integer count;

    public DateTime fromAsDateTime() {
        return DateTime.parse(from);
    }

    public DateTime toAsDateTime() {
        return DateTime.parse(to);
    }

}
