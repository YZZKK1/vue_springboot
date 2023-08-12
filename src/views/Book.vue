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
      <el-table-column prop="name" label="名称"  />
      <el-table-column prop="price" label="单价"  />
      <el-table-column prop="author" label="作者" />
      <el-table-column prop="createTime" label="出版时间" />
      <el-table-column  label="封面" >
        <template #default="scope">
          <el-image style="width: 100px; height: 100px" :src="scope.row.cover" :preview-src-list=[scope.row.cover] />

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
          <el-form-item label="名称">
            <el-input v-model="form.name" />
          </el-form-item>
          <el-form-item label="单价">
            <el-input v-model="form.price" />
          </el-form-item>
          <el-form-item label="作者">
            <el-input v-model="form.author" />
          </el-form-item>
          <el-form-item label="出版时间">
            <el-date-picker v-model="form.createTime" value-format="YYYY-MM-DD" type="date" style="width:80%" clearable/>
          </el-form-item>
          <el-form-item label="封面">
            <el-upload action="http://localhost:9090/files/upload"  :on-success="fileUploadSuccess">
              <el-button type="primary">Click to upload</el-button>
              <template #tip>
                <div class="el-upload__tip">
                  jpg/png files with a size less than 500KB.
                </div>
              </template>
            </el-upload>
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
  name: 'Book',
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
    fileUploadSuccess(res){

      console.log(res)
      this.form.cover=res.data
    },
    load(){
      request.get("/book", {
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
      if( this.$refs['upload'])
      { this.$refs['upload'].clearFiles()}


    },
    save(){
      if(this.form.id){
        request.put("/book",this.form).then(res=>{
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
        request.post("/book", this.form).then(res => {
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
      request.delete("/book/"+id).then(res=>{
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
      this.$nextTick(() => {
        if (this.$refs['upload']) {
          this.$refs['upload'].clearFiles()  // 清除历史文件列表
        }
      })
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