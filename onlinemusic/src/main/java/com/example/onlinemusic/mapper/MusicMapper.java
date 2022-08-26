package com.example.onlinemusic.mapper;

import com.example.onlinemusic.model.Music;
import jdk.internal.dynalink.linker.LinkerServices;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MusicMapper {
    // insert music file into music table, parameters match the fields in music table
    public int insert(String title, String singer, String time, String url, int userid);

    // TODO: 查询数据库中，是否已经存在了该首歌曲

    // delete one song from music table.
    public int delete(int id);
}
