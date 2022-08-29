package com.example.onlinemusic.controller;

import com.example.onlinemusic.mapper.LoveMusicMapper;
import com.example.onlinemusic.model.Music;
import com.example.onlinemusic.model.User;
import com.example.onlinemusic.tools.Constant;
import com.example.onlinemusic.tools.ResponseBodyMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/lovemusic")
public class LoveMusicController {

    // @Autowired
    @Resource
    private LoveMusicMapper loveMusicMapper;

    @RequestMapping("/likeMusic")
    public ResponseBodyMessage<Boolean> likeMusic(@RequestParam String id, HttpServletRequest request) {
        // cast String id to integer musicId
        int musicId = Integer.parseInt(id);
        System.out.println("musicId 是" + musicId);
        // 登录以后，从session里面拿到 userId

        // 获取session，检查是否登录
        HttpSession httpSession = request.getSession();
        if(httpSession == null || httpSession.getAttribute(Constant.USERINFO_SESSION_KEY) == null) {
            System.out.println("没有登录！");
            return new ResponseBodyMessage<>(-1, "Please log in to upload!", false);
        }

        User user = (User) httpSession.getAttribute(Constant.USERINFO_SESSION_KEY);
        int userId = user.getId();
        System.out.println("userId" + userId);

        Music music = loveMusicMapper.findLoveMusicByMusicAndUserId(userId, musicId);
        if (music != null) {
            // music was saved/loved, can't be saved again.
            // TODO: 加一个取消收藏的功能，delete it from lovemusic table
            return new ResponseBodyMessage<>(-1, "Music was saved/loved.", false);
        }
        Boolean effect = loveMusicMapper.insertLoveMusic(userId, musicId);
        if (effect) {
            return new ResponseBodyMessage<>(0, "收藏成功！", true);
        }else {
            return new ResponseBodyMessage<>(-1, "Failed to save/love the music!", false);
        }
    }

    @RequestMapping("/findlovemusic")
    public ResponseBodyMessage<List<Music>> findLoveMusic(@RequestParam(required = false) String musicName,
                                                          HttpServletRequest request) {
        // 1. check if a user logged in
        HttpSession httpSession = request.getSession(false);
        if(httpSession == null || httpSession.getAttribute(Constant.USERINFO_SESSION_KEY) == null) {
            System.out.println("没有登录！");
            return new ResponseBodyMessage<>(-1, "Please log in to search music!", null);
        }

        User user = (User) httpSession.getAttribute(Constant.USERINFO_SESSION_KEY);
        int userId = user.getId();
        System.out.println("userId" + userId);

        List<Music> musicList = null;
        if (musicName == null) {
            musicList = loveMusicMapper.findLoveMusicByUserId(userId);
        }else {
            musicList = loveMusicMapper.findLoveMusicByKeyAndUID(musicName, userId);
        }

        return new ResponseBodyMessage<>(0,"Find all music", musicList);
    }

    /*
     * @param id is music id
     *
     */
    @RequestMapping("/deletelovemusic")
    public ResponseBodyMessage<Boolean> deleteLoveMusic(@RequestParam String id, HttpServletRequest request) {
        int musicId = Integer.parseInt(id);
        HttpSession httpSession = request.getSession(false);
        if(httpSession == null) {
            System.out.println("没有登录！");
            return new ResponseBodyMessage<>(-1, "Please log in to delete the lovemusic list!", false);
        }

        User user = (User) httpSession.getAttribute(Constant.USERINFO_SESSION_KEY);
        int userId = user.getId();
        System.out.println("userId" + userId);

        int ret = loveMusicMapper.deleteLoveMusic(userId, musicId);
        if(ret == 1) {
            return new ResponseBodyMessage<>(0, "Succeed to delete the lovemusic", true);
        }else {
            return new ResponseBodyMessage<>(-1, "Failed to delete the lovemusic", false);
        }
    }
}
