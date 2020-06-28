package com.gloryjewel.book.web.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelloResponseDtoTests {

    @Test
    public void create(){

        String name = "test";
        int amount = 1000;

        HelloResponseDto helloResponseDto = new HelloResponseDto(name,amount);

        assertEquals(helloResponseDto.getName(), name);
        assertEquals(helloResponseDto.getAmount(), amount);

    }
}