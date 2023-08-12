<template>
  <div style="padding:10px">
    <div style="margin:10px 0">
      <el-button type="primary" @click="add">add</el-button>
      <el-button type="primary">input</el-button>
      <el-button type="primary">output</el-button>
    </div>

    <div style="margin:10px 0">
      <el-input v-model="search" placeholder="please type the key word" style="width:20%" clearable></el-input>
      <el-button type="primary" style="margin-left: 5px" @click="load">search</el-button>
    </div>

    <el-table :data="tableData" stripe style="width: 100%">
      <el-table-column prop="id" label="ID" sortable />
      <el-table-column prop="username" label="用户名"  />
      <el-table-column prop="nickName" label="昵称" />
      <el-table-column prop="age" label="年龄" />
      <el-table-column prop="sex" label="性别" />
      <el-table-column prop="address" label="地址" />
      <el-table-column label="角色">
        <template #default="scope">
          <span v-if="scope.row.role===1">管理员</span>
          <span v-if="scope.row.role===2">普通用户</span>
        </template>
      </el-table-column>
    <el-table-column fixed="right" label="操作" width="120">
    <template #default="scope">
      <el-button link type="primary"  @click="handleEdit(scope.row)">编辑</el-button>
      <el-popconfirm
          confirm-button-text="好的"
          cancel-button-text="不用"
          :icon="InfoFilled"
          icon-color="#626AEF"
          title="删除?"
          @confirm="handleDelete(scope.row.id)"
      >
        <template #reference>
          <el-button type="text" >删除</el-button>
        </template>
      </el-popconfirm>
    </template>
    </el-table-column>
    </el-table>
    <div margin="10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          v-model:current-page="currentPage"
          :page-sizes="[5 ,10 ,20]"
          v-model:page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
        </el-pagination>
        <el-dialog v-model="dialogVisible" title="Tips" width="30%">
          <el-form :model="form" label-width="120px">
            <el-form-item label="用户名">
              <el-input v-model="form.username" />
            </el-form-item>
            <el-form-item label="昵称">
              <el-input v-model="form.nickName" />
            </el-form-item>
            <el-form-item label="年龄">
              <el-input v-model="form.age" />
            </el-form-item>
            <el-form-item label="性别">
                <el-radio v-model="form.sex" label="男">男</el-radio>
                <el-radio v-model="form.sex" label="女">女</el-radio>
                <el-radio v-model="form.sex" label="未知">未知</el-radio>
            </el-form-item>
            <el-form-item label="地址">
              <el-input type="textarea" v-model="form.address" />
            </el-form-item>
          </el-form>
          <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="save">
            确定
          </el-button>
        </span>
        </template>
      </el-dialog>
    </div>
  </div>
</template>

<script>


import request from "@/utils/request";

export default {
  name: 'HomeView',
  components: {
  },
  data(){
    return{
      form:{},
      dialogVisible:false,
      search:'',
      currentPage:1,
      pageSize:10,
      total:0,
      tableData:[]
    }
  },
  created(){
    this.load()
  },
  methods:{
    load(){
      request.get("/user", {
        params:{
          pageNum:this.currentPage,
          pageSize:this.pageSize,
          search:this.search}
        }).then(res => {
        console.log(res)
        this.tableData=res.data.records
        this.total=res.data.total
        })


    },
    add(){
      this.dialogVisible=true
      this.form={}

    },
    save(){
      if(this.form.id){
        request.put("/user",this.form).then(res=>{
        console.log(res)
          if(res.code==="0"){  this.$messageBox({
            type:"success",
            message:"更新成功"

          })}
          else{  this.$messageBox({
            type:"error" ,
            message:res.msg

          })}
          this.load()
          this.dialogVisible=false
        })}
      else {
        request.post("/user", this.form).then(res => {
          console.log(res)
          if(res.code==="0"){  this.$messageBox({
            type:"success",
            message:"新增成功"

          })}
        else{  this.$messageBox({
            type:"error" ,
            message:res.msg

          })}
        this.load()
          this.dialogVisible=false
        })
      }

    },
    handleDelete(id){
      console.log(id)
      request.delete("/user/"+id).then(res=>{
        if(res.code==="0"){  this.$messageBox({
          type:"success",
          message:"删除成功"

        })}
        else{  this.$messageBox({
          type:"error" ,
          message:res.msg

        })}this.load()

      })
    },

    handleEdit(row){
      this.form=JSON.parse(JSON.stringify(row))
      this.dialogVisible=true
    },
    handleSizeChange(pageSize){
      this.pageSize=pageSize
      this.load()
    },
    handleCurrentChange(pageNum){
      this.currentPage=pageNum
      this.load()
    }

  }
}

</script>
