-- 在云服务器上，部署mysql。
-- 安装步骤：https://cloud.tencent.com/developer/article/1496725
-- 安装视频：https://www.youtube.com/watch?v=B9be7XBiYQU
-- https://www.youtube.com/watch?v=B9be7XBiYQU
-- wget https://dev.mysql.com/get/mysql80-community-release-el7-7.noarch.rpm  (find download link in mysql yum install)
-- rpm -ivh mysql80-community-release-el7-7.noarch.rpm
-- cd /etc/yum.repos.d/
-- ll to check mysql
-- it will show two files: mysql-community.repo
--                         mysql-community-source.repo

-- start mysql: systemctl enable mysqld.service

-- 输入CLI:
--  mysql -u root -p
-- Enter password:

-- create database for online music
drop database if exists `onlinemusic`;
create database if not exists `onlinemusic` character set utf8;

-- use database music server
use `onlinemusic`;

-- create user table
drop table if exists `user`;
create table `user` (
    `id` int primary key auto_increment,
    `username` varchar(20) not null,
    `password` varchar(255) not null
);

-- create music table
drop table if exists `music`;
create table `music` (
    `id` int primary key auto_increment,
    `title` varchar(50) not null,       -- title of the song
    `singer` varchar(30) not null,
    `time` varchar(13) not null,
    `url` varchar(1000) not null,       -- song is stored in this url
    `userid` int(11) not  null          -- who uploaded this song
);

-- create favorite music list
drop table if exists `lovemusic`;
create table `lovemusic` (
    `id` int primary key auto_increment,
    `user_id` int(11) not null,
    `music_id` int(11) not null
);



