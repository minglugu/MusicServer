package com.example.onlinemusic.tools;

import lombok.Data;

// generic class
@Data
public class ResponseBodyMessage <T> {
    private int status; // status code, such as 1, 2, 3
    private String message; // describes the status[error message, or ok message]
    private T data; // returned data [different types]

    public ResponseBodyMessage(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
