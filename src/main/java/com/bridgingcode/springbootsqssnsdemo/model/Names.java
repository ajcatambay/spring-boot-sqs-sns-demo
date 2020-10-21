package com.bridgingcode.springbootsqssnsdemo.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

public class Names {

    String[] names;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    private ObjectMapper mapper = new ObjectMapper();

    public String[] getNames() {
        return names;
    }

    public void setNames(String[] names) {
        this.names = names;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String toJsonString() throws JsonProcessingException {
        return mapper.writeValueAsString(this);
    }
}
