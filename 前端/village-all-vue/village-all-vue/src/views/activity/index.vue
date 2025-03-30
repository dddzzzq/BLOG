<!--后台活动页面-->
<template>
  <div style="width: 18px;height: 18px;margin-left: 20px;margin-top: 10px">
    <el-tooltip  effect="dark" content="活动新增" placement="bottom" style="width: 30px">
      <el-button type="primary" @click="handleAdd()"><img src="@/assets/add.png" alt="" style="width: 18px; height: 18px;margin-right: 2px"></el-button>
    </el-tooltip>
  </div>

  <div>
    <el-table :data="tableData.slice((currentPage - 1)*pageSize, currentPage*pageSize)" style="margin-left: 10px;margin-top: 30px" border stripe>
      <el-table-column prop="id" label="序号" width="70" align="center" sortable></el-table-column>
      <el-table-column prop="name" label="活动名称"></el-table-column>
      <el-table-column prop="descr" label="活动简介" show-overflow-tooltip></el-table-column>
      <el-table-column prop="cover" label="封面">
        <template v-slot="scope">
          <div style="display: flex; align-items: center">
            <el-image style="width: 50px; height: 30px; border-radius: 5px"
                      v-if="scope.row.cover"
                      :src="scope.row.cover"
                      :preview-src-list="[scope.row.cover]"
                      :initial-index="4"
            ></el-image>
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="start" label="开始时间"></el-table-column>
      <el-table-column prop="end" label="结束时间"></el-table-column>
      <el-table-column prop="form" label="活动形式"></el-table-column>
      <el-table-column prop="address" label="活动地址" show-overflow-tooltip></el-table-column>
      <el-table-column prop="host" label="发起人"></el-table-column>
      <el-table-column prop="readCount" label="浏览量"></el-table-column>
      <el-table-column label="操作" align="center" width="180">
        <template v-slot="scope">
          <el-button size="mini" type="primary"  @click="handleEdit(scope.row)">编辑</el-button>
          <el-button size="mini" type="danger"  @click="del(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div style="display: flex; justify-content: right">
      <el-pagination
          background
          layout="prev, pager, next"
          :total="tableData.length"
          :page-size="pageSize"
          @current-change="PageChange"
          style="margin-top: 5px;"
      />
    </div>
  </div>


  <el-dialog title="信息" v-model="fromVisible" width="60%" :close-on-click-modal="false" destroy-on-close>
    <el-form :model="form" label-width="100px" style="padding-right: 50px" :rules="rules" ref="formRef">
      <el-form-item label="活动名称" prop="name">
        <el-input v-model="form.name" placeholder="活动名称"></el-input>
      </el-form-item>
      <el-form-item label="活动简介" prop="descr">
        <el-input type="textarea" v-model="form.descr" placeholder="活动简介"></el-input>
      </el-form-item>
      <el-form-item label="封面" prop="cover">
        <el-upload
            :action="'http://localhost:80/file/upload'"
            :headers="{ token: user.token }"
            list-type="picture"
            :on-success="handleCoverSuccess"
        >
          <el-button type="primary">上传封面</el-button>
        </el-upload>
      </el-form-item>
      <el-form-item label="开始时间" prop="start">
        <el-date-picker style="width: 100%" value-format="YYYY-MM-DD" format="YYYY-MM-DD" v-model="form.start" placeholder="开始时间"></el-date-picker>
      </el-form-item>
      <el-form-item label="结束时间" prop="end">
        <el-date-picker style="width: 100%" value-format="YYYY-MM-DD" format="YYYY-MM-DD" v-model="form.end" placeholder="结束时间"></el-date-picker>
      </el-form-item>
      <el-form-item label="活动形式" prop="form">
        <el-input v-model="form.form" placeholder="活动形式"></el-input>
      </el-form-item>
      <el-form-item label="活动地址" prop="address">
        <el-input v-model="form.address" placeholder="活动地址"></el-input>
      </el-form-item>
      <el-form-item label="发起人" prop="host">
        <el-input v-model="form.host" placeholder="发起人"></el-input>
      </el-form-item>
      <el-form-item label="内容" prop="content">
        <div id="editor2"></div>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="fromVisible = false">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </div>
  </el-dialog>

</template>

<script>
import request from "@/utils/api";
import E from "wangeditor";
import hljs from "highlight.js";

export default {
  name: "index",
  data(){
    return{
       form:{},
      rules: {
        name: [
          {required: true, message: '请输入活动名称', trigger: 'blur'},
        ],
        descr: [
          {required: true, message: '请输入活动简介', trigger: 'blur'},
        ],
        start: [
          {required: true, message: '请输入活动开始时间', trigger: 'blur'},
        ],
        end: [
          {required: true, message: '请输入活动结束时间', trigger: 'blur'},
        ],
        address: [
          {required: true, message: '请输入活动地址', trigger: 'blur'},
        ],
        host: [
          {required: true, message: '请输入活动主办方', trigger: 'blur'},
        ],
      },
      fromVisible:false,
      tableData:[],
      editor:null,
      user: JSON.parse(localStorage.getItem('user') || '{}'),
      pageSize: 5,
      currentPage: 1
    }
  },
  created(){
    if(this.user.authority==undefined||this.user.authority!='最高管理员'){
      this.$router.push("/")
      this.$message.error("权限不足！")
    }
  this.load();
  },
  methods:{
    //封面上传
    handleCoverSuccess(res) {
      this.form.cover = res.data
    },
    del(id) {   // 单个删除
      this.$confirm('您确定删除吗？', '确认删除', {type: "warning"}).then(response => {
        request.get('/act/del/' + id).then(res => {
          this.$message.success('操作成功')
          this.load()
        })
      }).catch(() => {
      })
    },
    save() {
      console.log(this.form)
      //表单校验
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.form.content = this.editor.txt.html()
          request({
            url: this.form.id ? '/act/up' : '/act/ins',
            method:'POST',
            data: this.form
          }).then(res => {
            this.$message.success('保存成功')
            this.load()
            this.fromVisible = false
          })
        }
      })
    },
    handleEdit(row) {   // 编辑数据
      this.form = JSON.parse(JSON.stringify(row))  // 给form对象赋值  注意要深拷贝数据
      this.fromVisible = true   // 打开弹窗
      this.$nextTick(()=>{
        this.editor=new E('#editor2');
        //设置上传图片
        this.editor.config.uploadImgServer='http://localhost:80/file/editor/upload';
        this.editor.config.uploadFileName='file';
        this.editor.config.uploadImgHeaders={
          token:this.user.token
        }

        //设置上传视频
        this.editor.config.uploadVideoServer='http://localhost:80/file/editor/videoupload';
        this.editor.config.uploadVideoName='file';
        this.editor.config.uploadVideoHeaders={
          token:this.user.token
        }
        //配置高亮
        this.editor.highlight=hljs;
        this.editor.create();
      })
      setTimeout(() => {
        this.editor.txt.html(this.form.content)
      }, 0)
    },
    load(){
      request.get("/act/all").then(res=>{
        this.tableData=res
      })
    },
    handleAdd() {
      this.form = {}
      this.fromVisible = true
      this.$nextTick(()=>{
        this.editor=new E('#editor2');
        //设置上传图片
        this.editor.config.uploadImgServer='http://localhost:80/file/editor/upload';
        this.editor.config.uploadFileName='file';
        this.editor.config.uploadImgHeaders={
          token:this.user.token
        }

        //设置上传视频
        this.editor.config.uploadVideoServer='http://localhost:80/file/editor/videoupload';
        this.editor.config.uploadVideoName='file';
        this.editor.config.uploadVideoHeaders={
          token:this.user.token
        }
        //配置高亮
        this.editor.highlight=hljs;
        this.editor.create();
      })

    },
    PageChange(val) {
      this.currentPage = val
    }
  }
}
</script>

<style>
::v-deep .el-table .el-table__cell {
  z-index: auto !important;
}
</style>