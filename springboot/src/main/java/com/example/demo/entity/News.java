package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@TableName("news")
@Data//set get方法创建
public class News {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private String title;
    private String content;
    private String author;
    @JsonFormat(pattern = "YYYY-MM-DD HH:mm:ss",timezone = "GMT+8")
    private Date dateTime;


}