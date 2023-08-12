<template>
  <div style="padding:10px">
    <div style="margin:10px 0">
      <el-button type="primary" @click="add">add</el-button>
    </div>

    <div style="margin:10px 0">
      <el-input v-model="search" placeholder="please type the key word" style="width:20%" clearable></el-input>
      <el-button type="primary" style="margin-left: 5px" @click="load">search</el-button>
    </div>

    <el-table :data="tableData" stripe style="width: 100%">
      <el-table-column prop="id" label="ID" sortable />
      <el-table-column prop="title" label="标题"  />
      <el-table-column prop="author" label="作者" />
      <el-table-column prop="dateTime" label="时间" />
      <el-table-column label="操作" width="200">
        <template #default="scope">
          <el-button  type="text"  @click="details(scope.row)">详情</el-button>
          <el-button  type="text"  @click="handleEdit(scope.row)">编辑</el-button>
          <el-popconfirm
              confirm-button-text="好的"
              cancel-button-text="不用"
              :icon="InfoFilled"
              icon-color="#626AEF"
              title="删除?"
              @confirm="handleDelete(scope.row.id)"
          >
            <template #reference>
              <el-button  type="text" >删除</el-button>
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
      <el-dialog v-model="dialogVisible" title="News" width="50%">
        <el-form :model="form" label-width="120px">
          <el-form-item label="标题">
            <el-input v-model="form.title" style="width:50%"/>
          </el-form-item>

          <div id="div1"></div>
          <!--        <el-form-item label="内容">-->
          <!--          <el-input v-model="form.price" style="width: 80%"></el-input>-->
          <!--        </el-form-item>-->
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
      <el-dialog v-model="vis" title="details" width="50%">
        <el-card>
          <div v-html="detail.content" style="min-height: 50px"></div>
        </el-card>
      </el-dialog>
    </div>
  </div>
</template>

<script>


import request from "@/utils/request";
import E from 'wangeditor'
let editor;
export default {
  name: 'News',
  components: {},
  data(){
    return{
      form:{},
      dialogVisible:false,
      search:'',
      currentPage:1,
      pageSize:10,
      total:0,
      tableData:[],
      vis:false,
      detail:{}

    }
  },
  created(){
    this.load()
  },
  methods:{
    details(row)
    {this.detail=row
      this.vis=true

    },
    load(){
      request.get("/news", {
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
      this.$nextTick(()=>{
        if(editor!=null){editor.destroy()}
        editor = new E('#div1')
        editor.config.uploadImgServer='http://localhost:9090/files/editor/upload'
        editor.config.uploadFileName="file"
        editor.create()})


    },
    save(){
      this.form.content=editor.txt.html()



      if(this.form.id){
        request.put("/news",this.form).then(res=>{
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
        let userStr = sessionStorage.getItem("user") || "{}"
        let user=JSON.parse(userStr)
        this.form.author = user.nickName


        request.post("/news", this.form).then(res => {
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
      request.delete("/news/"+id).then(res=>{
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

      this.$nextTick(()=>{
        if(editor!=null){editor.destroy()}
        editor = new E('#div1')
        editor.config.uploadImgServer='http://localhost:9090/files/editor/upload'
        editor.config.uploadFileName="file"
        editor.create()
        editor.txt.html(row.content)
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