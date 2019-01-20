package org.mcvly.refactoring.first_example.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class Invoice
{
    private final String customer;
    private final List<Performance> performances;

    @JsonCreator
    public Invoice(@JsonProperty("customer") String customer, @JsonProperty("performances") List<Performance> performances)
    {
        this.customer = customer;
        this.performances = performances;
    }

}
