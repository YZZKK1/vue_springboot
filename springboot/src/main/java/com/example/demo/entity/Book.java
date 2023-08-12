package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@TableName("book")
@Data//set get方法创建
public class Book {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private String name;
    private BigDecimal price;
    private String author;
    @JsonFormat(pattern = "YYYY-MM-DD",timezone = "GMT+8")
    private Date createTime;
    private String cover;



}
