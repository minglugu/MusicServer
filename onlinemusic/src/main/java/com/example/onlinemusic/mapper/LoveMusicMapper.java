package com.example.onlinemusic.mapper;

import com.example.onlinemusic.model.Music;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LoveMusicMapper {
    // 查询该用户，将要收藏的音乐，是否被收藏过。收藏过，则不能添加
    Music findLoveMusicByMusicAndUserId(int userId, int musicId);

    // 没有收藏过，插入数据库中的一条记录
    boolean insertLoveMusic(int userId, int musicId);

    // 查询该用户下，所有收藏的喜爱的音乐
    List<Music> findLoveMusicByUserId(int userId);

    // 查询当前用户，指定为musicName的音乐，并支持模糊查询
    List<Music> findLoveMusicByKeyAndUID(String musicName, int userId);

    // 取消某个用户喜欢的音乐收藏
    // return 返回收影响的行数
    int deleteLoveMusic(int userId, int musicId);

    // 当删除库中的音乐时，同步删除lovemusic表中的数据, 返回值为删除了多少条数据
    int deleteLoveMusicByMusicId(int musicId);
}
