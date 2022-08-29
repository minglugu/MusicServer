package com.example.onlinemusic.mapper;

import com.example.onlinemusic.model.Music;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoveMusicMapper {
    // 1. 查询将要收藏的音乐，是否被收藏过。收藏过，则不能添加
    Music findLoveMusicByMusicAndUserId(int userId, int musicId);

    // 2. 没有收藏过，插入数据库中的一条记录
    boolean insertLoveMusic(int userId, int musicId);

    // 3. 取消收藏的音乐
    boolean deleteLoveMusic(int userId, int musicId);
}
