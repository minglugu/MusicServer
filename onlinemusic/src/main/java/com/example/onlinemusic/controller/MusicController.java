package com.example.onlinemusic.controller;

import com.example.onlinemusic.tools.Constant;
import com.example.onlinemusic.tools.ResponseBodyMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/music")
public class MusicController {
    // 使用@Value("${music.local.path}"), 获取到配置文件当中的值。
    @Value("${music.local.path}")
    private String SAVE_PATH;
    // 音乐保存的路径, 用“/”
    // 这个路径可以封装到application.properties文件里
    // private String SAVE_PATH = "C:/Users/LuLu/music1/";

    // 前端那里会获取upload.html里面 <input type="file" name="filename"/> filename这个属性，否则不会获取这个文件。
    @RequestMapping("/upload")
    public ResponseBodyMessage<Boolean> insertMusic(@RequestParam String singer,
                                                    @RequestParam("filename") MultipartFile file,
                                                    HttpServletRequest request) {

        // 1. 检查是否登录了
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute(Constant.USERINFO_SESSION_KEY) == null) {
            System.out.println("No login yet!");
            return new ResponseBodyMessage<>(-1, "请登陆后，再上传音乐文件", false);
        }

        // 2. 上传到服务器
        String fileNameAndType = file.getOriginalFilename(); // 获取完整的文件名称: xxx.mp3

        System.out.println("fileNameAndType: " + fileNameAndType);

        String path = SAVE_PATH + fileNameAndType;

        File dest = new File(path);
        // 不存在的情况下,创建一个目录
        if(!dest.exists()) {
            // 创建一个文件
            dest.mkdir();
        }
        try {
            file.transferTo(dest); // 上传文件到目的地
            return new ResponseBodyMessage<>(0, "上传成功！",true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseBodyMessage<>(-1, "上传失败", false);

        // 3. 上传成功后，把文件写到数据库里面。
    }
}
