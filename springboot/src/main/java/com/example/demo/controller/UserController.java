package com.example.demo.controller;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.LambdaUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.sun.tools.javac.code.Type;
import org.apache.ibatis.annotations.Delete;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    @Resource
    UserMapper userMapper;


    @PostMapping("/register")
    public Result<?> register(@RequestBody User user){//转换为java对象

        User res=userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername,user.getUsername()).eq(User::getPassword,user.getPassword()));
        if(res!=null){return Result.error(String.valueOf(-1),"Duplicate username");}
        userMapper.insert(user);
        return Result.success();
    }

    @PostMapping("/login")
    public Result<?> login(@RequestBody User user){//转换为java对象
        User res=userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername,user.getUsername()).eq(User::getPassword,user.getPassword()));
        if(res==null){return Result.error(String.valueOf(-1),"User name or password mistake");}
        return Result.success(res);
    }
    @PostMapping
    public Result<?> save(@RequestBody User user){//转换为java对象
        if(user.getPassword()==null){
            user.setPassword("123456");
        }
        userMapper.insert(user);
        return Result.success();
    }
    @PutMapping
    public Result<?> update(@RequestBody User user){
        userMapper.updateById(user);
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result<?> update(@PathVariable Long id){
        userMapper.deleteById(id);
        return Result.success();
    }
    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id){

        return Result.success(userMapper.selectById(id));
    }








    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam (defaultValue = "10")Integer pageSize,
                              @RequestParam (defaultValue = "")String search){
        LambdaQueryWrapper<User> wrapper=Wrappers.<User>lambdaQuery();
        if(StrUtil.isNotBlank(search)){
            wrapper.like(User::getNickName,search);
        }
       Page<User>userPage=userMapper.selectPage(new Page<>(pageNum,pageSize),wrapper);
        return Result.success(userPage);
    }
}
