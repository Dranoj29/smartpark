package com.dranoj.SmartPark.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ResponseUtil {

    public static ResponseEntity<Map<String, Object>> buildErrorResponse(HttpStatus httpStatus, String message){
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        return ResponseEntity.status(httpStatus).body(response);
    }
}
