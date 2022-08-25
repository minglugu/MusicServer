package com.example.onlinemusic.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MusicMapper {
    // insert music file into music table, parameters match the fields in music table
    public int insert(String title, String singer, String time, String url, int userid);
}
