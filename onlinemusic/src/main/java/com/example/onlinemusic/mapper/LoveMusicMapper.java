package com.example.onlinemusic.mapper;

import com.example.onlinemusic.model.Music;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LoveMusicMapper {
    // 1. 查询该用户，将要收藏的音乐，是否被收藏过。收藏过，则不能添加
    Music findLoveMusicByMusicAndUserId(int userId, int musicId);

    // 2. 没有收藏过，插入数据库中的一条记录
    boolean insertLoveMusic(int userId, int musicId);

    // 3. 取消收藏的音乐
    boolean deleteLoveMusic(int userId, int musicId);

    // 4. 查询该用户下，所有收藏的喜爱的音乐
    List<Music> findLoveMusicByUserId(int userId);

    // 5. 查询当前用户，指定为musicName的音乐，并支持模糊查询
    List<Music> findLoveMusicByKeyAndUID(String musicName, int userId);
}
