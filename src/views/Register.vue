<template>
  <div style="width:100%;height:100vh;background-color:dodgerblue;overflow:hidden">
    <div style="width:400px; margin:150px auto">
      <div style="color:white;text-align:center;padding:30px;font-size:30px">欢迎注册</div>
      <el-form :model="form" size="default" :rules="rules">
        <el-form-item prop="username">
          <el-input prefix-icon="UserFilled"  v-model="form.username" placeholder="User"></el-input>
        </el-form-item >
        <el-form-item prop="password">
          <el-input prefix-icon="Lock" v-model="form.password" placeholder="Password" show-password/>
        </el-form-item>
        <el-form-item prop="confirm">
          <el-input prefix-icon="Lock" v-model="form.confirm"  placeholder="Please enter the password again" show-password/>
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
        confirm:[{required:true,message:"Please enter password",trigger:'blur'},
        ],
      }


    }
  },
  methods:{

    register(){
      if(this.from.password!==this.from.confirm){
        this.$messageBox({
          type:"error" ,
          message:"两次密码不一致"

        })
        return

      }
      request.post("/user/register",this.form).then(res=>{
        if(res.code==='0'){
          this.$messageBox({
            type:"success",
            message:"注册成功"

          })
          this.$router.push({path:"/login"})//成功后的的页面跳转

        }else{
          this.$messageBox({
            type:"error" ,
            message:res.msg



          })
        }
      })
    }}}

</script>

<style scoped>

</style>