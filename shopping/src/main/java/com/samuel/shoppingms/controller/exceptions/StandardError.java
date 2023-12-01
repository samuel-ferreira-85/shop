package com.samuel.shoppingms.controller.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class StandardError {

    private Integer status;
    private OffsetDateTime timestamp;
    private String type;
    private String title;
    private String detail;
    private String message;
    private List<Field> fields;

    @Getter
    @Builder
    public static class Field {
        private String name;
        private String message;
    }
}
