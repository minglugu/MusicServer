package com.example.onlinemusic.model;

import lombok.Data;

/**
 * @Author Minglu Gu
 * @Date 2022/08/22
 * @Dscription User class, attributes match the fields of User table in database
 */
@Data
public class User {
    private int id;
    private String username;
    private String password;
}
