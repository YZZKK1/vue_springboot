<template>
  <div style="width:100%;height:100vh;background-color:dodgerblue;overflow:hidden">
      <div style="width:400px; margin:150px auto">
        <div style="color:white;text-align:center;padding:30px;font-size:30px">欢迎登录</div>
        <el-form :model="form" size="default" :rules="rules" ref="form">
          <el-form-item prop="username">
            <el-input prefix-icon="UserFilled"  v-model="form.username" placeholder="User" ></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input prefix-icon="Lock" v-model="form.password" placeholder="Password" show-password/>
          </el-form-item>
          <el-form-item >
            <el-button style="width:100%" @click="login">登录</el-button>
          </el-form-item>

        </el-form>
      </div>
  </div>

</template>



<script>
import request from "@/utils/request";
import router from "@/router";

export default {
  name: "Login",
  data(){
    return{
      form:{},
      rules:{
        username:[{required:true,message:"Please enter username",trigger:'blur'},
            ],
        password:[{required:true,message:"Please enter password",trigger:'blur'},
        ],
      }


    }
    },created() {
       sessionStorage.removeItem("user")
  },
  methods:{
    login(){
      this.$refs['form'].validate((valid)=>{
      if(valid){
        request.post("/user/login",this.form).then(res=>{
          if(res.code==='0'){
            this.$messageBox({
              type:"success",
              message:"登录成功"

            })
            sessionStorage.setItem("user",JSON.stringify(res.data))
            this.$router.push({path:"/"})//成功后的的页面跳转

          }else{
            this.$messageBox({
              type:"error",
              message:res.msg



            })
          }
        })



      }
    })









  }}}

</script>

<style scoped>

</style>