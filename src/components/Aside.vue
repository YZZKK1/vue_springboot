<template>
  <div>
  <el-row class="tac">
    <el-col :span="40">
      <h5 class="mb-2"></h5>
      <el-menu
          style="min-height:100vh "
          :default-active="path"
          router
          class="el-menu-vertical-demo"

      >
        <el-sub-menu v-if="user.role===1">
          <template #title>
            <el-icon><location /></el-icon>
            <span style="font-weight:bold;color:#646363">System Management</span>
          </template>
            <el-menu-item index="/home" v-if="user.role===1">Person management</el-menu-item>
        </el-sub-menu>
        <el-menu-item index="/book">
          <el-icon><Menu /></el-icon>
          <span style="font-weight:bold;color:#444444">Data Management</span>
        </el-menu-item>
        <el-menu-item index="/news">
          <el-icon><document /></el-icon>
          <span style="font-weight:bold;color:#444444">新闻管理</span>
        </el-menu-item>
        <el-menu-item index="4">
          <el-icon><setting /></el-icon>
          <span style="font-weight:bold;color:#444444">Navigator Four</span>
        </el-menu-item>
      </el-menu>
    </el-col>
  </el-row>
  </div>
</template>

<script >
import request from "@/utils/request";

export default {
  name: "Aside",
  data(){
    return{
      user:{},
      path:this.$route.path

    }
  },
  created(){
    let userStr=sessionStorage.getItem("user")||"{}"
    this.user=JSON.parse(userStr)

      request.get("/user/"+this.user.id).then(res=>{
        if(res.code==="0"){
          this.user=res.data
        }
      })



  }
}

</script>


<style scoped>

</style>