package com.example.onlinemusic.controller;

import com.example.onlinemusic.mapper.MusicMapper;
import com.example.onlinemusic.model.User;
import com.example.onlinemusic.tools.Constant;
import com.example.onlinemusic.tools.ResponseBodyMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/music")
public class MusicController {
    // 使用@Value("${music.local.path}"), 获取到配置文件（application.properties）当中的值。
    @Value("${music.local.path}")
    private String SAVE_PATH;
    // 音乐保存的路径, 用“/”
    // 这个路径可以封装到application.properties文件里
    // private String SAVE_PATH = "C:/Users/LuLu/music1/";

    @Autowired
    private MusicMapper musicMapper;

    // 前端那里会获取upload.html里面 <input type="file" name="filename"/> filename这个属性，否则不会获取这个文件。
    @RequestMapping("/upload")
    public ResponseBodyMessage<Boolean> insertMusic(@RequestParam String singer,
                                                    @RequestParam("filename") MultipartFile file,
                                                    HttpServletRequest request) {

        // 1. 检查是否登录了
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute(Constant.USERINFO_SESSION_KEY) == null) {
            System.out.println("Not login yet!");
            return new ResponseBodyMessage<>(-1, "请登陆后，再上传音乐文件", false);
        }

        // TODO 先查询数据库中，是否有当前音乐。歌曲名和歌手是否已经在数据库中存在

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
            // return new ResponseBodyMessage<>(0, "上传成功！",true); // 上传文件到数据库的话，就不能return了
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseBodyMessage<>(-1, "上传失败", false);
        }
        // 上传文件到数据库的话，就不能return了
        // return new ResponseBodyMessage<>(-1, "上传失败", false);

        // 3. 上传成功后，把文件写到数据库里面。
        // 3.1 get the field data for a song
        // 3.1.1 get music title, 存进去的时候，没有加后缀".mp3", 在前端取到url以后，播放的时候，加一个后缀“.mp3”
        int indexDot = fileNameAndType.lastIndexOf("."); // find the "." before the file type
        String title = fileNameAndType.substring(0, indexDot);

        // 3.1.2
        User user = (User) session.getAttribute(Constant.USERINFO_SESSION_KEY);
        int userid = user.getId();

        // 3.2 播放音乐 -> http request
        String url = "/music.get?path="+title;

        // format the time year, month and date
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        // 把当前的日期/时间(new Date()) 格式化成 sf
        String time = sf.format(new Date());
        System.out.println("当前的时间为: " + time);

        // 3.2 调用MusicMapper中的insert()
        int ret = 0;
        ret = musicMapper.insert(title, singer, time, url, userid);

        if (ret == 1) {
            // TODO 这里应该跳转到音乐列表的页面
            return new ResponseBodyMessage<>(0, "成功上传到数据库！",true);
        } else {
            return new ResponseBodyMessage<>(-1, "上传数据库失败！",false);
        }

        // 重复上传歌曲，能否成功？ yes
    }

    // 播放音乐的时候，路径名为：/music/get?path=xxx.mp3
    @RequestMapping("/get")
    public ResponseEntity<byte[]> func(String path) {
        File file = new File(SAVE_PATH + "/" + path);
        byte[] a = null;
        try {
            a = Files.readAllBytes(file.toPath());
            if (a == null) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(a);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }
}
