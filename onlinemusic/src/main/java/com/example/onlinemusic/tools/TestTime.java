package com.example.onlinemusic.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

// time for music in music table
public class TestTime {
    public static void main(String[] args) {
        // format the time year, month and date
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        // 把当前的日期/时间(new Date()) 格式化成 sf
        String time = sf.format(new Date());
        System.out.println("当前的时间为: " + time);

        /*
        // format the time year, month, day, hour, minute, and second
        SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 把当前的日期/时间(new Date()) 格式化成 sf
        String time2 = sf2.format(new Date());
        System.out.println("当前的时间为: " + time2);
        */
    }
}
