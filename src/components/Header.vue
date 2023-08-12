<template>
<div style="height: 60px; line-height:60px; box-shadow: 0px 0px 2px 3px lightgrey;background-color:dodgerblue; display: flex">
  <div style="width:200px;padding-left:50px;font-weight:bold;color:white" >后台管理</div>
  <div style="flex:1" ></div>
  <div style="width:100px;">
    <el-dropdown style="line-height:60px;font-weight:bold;color:white">
    <span class="el-dropdown-link">
      {{user.nickName}}
      <el-icon class="el-icon--right">
        <arrow-down />
      </el-icon>
    </span>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item @click="$router.push('/person')">personal information</el-dropdown-item>
          <el-dropdown-item @click="$router.push('/login')">exit</el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>
  </div>
  </div>
</template>

<script >
export default {
  name: "Header",
  data() {
    return {
      user:{}

    }
  },
  created() {
    let userStr=sessionStorage.getItem("user")||"{}"
    this.user=JSON.parse(userStr)
  }
}
</script>

<style scoped>

</style>