<!--博客管理页-->
<template>
  <el-card style="margin-left: 10px;margin-right: 10px">
    <div style="display: flex">
      <div class="search" style="flex: 1">
        <el-input placeholder="请输入标题" style="width: 200px; margin-right: 10px" v-model="title"></el-input>
        <el-button type="primary"  style="margin-left: 10px" @click="s1()">查询</el-button>
        <el-button type="warning"  style="margin-left: 10px" @click="reset">重置</el-button>
      </div>
      <div class="search" style="flex: 1">
        <el-input placeholder="请输入标签" style="width: 200px; margin-right: 10px" v-model="tags"></el-input>
        <el-button type="primary"  style="margin-left: 10px" @click="s2()">查询</el-button>
        <el-button type="warning" style="margin-left: 10px" @click="reset">重置</el-button>
      </div>
      <div class="search" style="flex: 1">
        <el-input placeholder="请输入简介" style="width: 200px; margin-right: 10px" v-model="brief"></el-input>
        <el-button type="primary"  style="margin-left: 10px" @click="s3()">查询</el-button>
        <el-button type="warning"  style="margin-left: 10px" @click="reset">重置</el-button>
      </div>
    </div>
    <div style="display: flex;margin-top: 30px" >
      <div class="search" style="flex: 1;margin-left: 90px">
        <el-input placeholder="请输入分类" style="width: 200px; margin-right: 10px" v-model="categoryName"></el-input>
        <el-button type="primary"  style="margin-left: 10px" @click="s4()">查询</el-button>
        <el-button type="warning"  style="margin-left: 10px" @click="reset">重置</el-button>
      </div>
      <div class="search" style="flex: 1">
        <el-input placeholder="请输入发布人" style="width: 200px; margin-right: 10px" v-model="username"></el-input>
        <el-button type="primary"  style="margin-left: 10px" @click="s5()">查询</el-button>
        <el-button type="warning"  style="margin-left: 10px" @click="reset">重置</el-button>
      </div>
      <div class="operation">
        <el-button type="success"  @click="handleAdd">新增</el-button>
      </div>
    </div>



  </el-card>
  <div>
<!-- =========================   主表=================================-->
    <div class="table" style="margin-left: 20px;margin-top: 20px">
      <el-table :data="tableData.slice((currentPage - 1)*pageSize, currentPage*pageSize)" border stripe>

        <el-table-column prop="id" label="序号" width="70" align="center" sortable></el-table-column>
        <el-table-column prop="title" label="标题" show-overflow-tooltip></el-table-column>
        <el-table-column prop="descr" label="简介" show-overflow-tooltip></el-table-column>
        <el-table-column prop="cover" label="封面">
          <template v-slot="scope">
            <div style="display: flex; align-items: center">
              <el-image
                  style="width: 50px; height: 50px; border-radius: 5px"
                  v-if="scope.row.cover"
                  :src="scope.row.cover"
                  :initial-index="4"
                  :preview-src-list="[scope.row.cover]"></el-image>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="categoryName" label="分类"></el-table-column>
        <el-table-column prop="tags" label="标签">
          <template v-slot="scope">
            <el-tag v-for="item in JSON.parse(scope.row.tags || '[]')" :key="item" style="margin-right: 5px">{{ item }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="userName" label="发布人"></el-table-column>
        <el-table-column prop="date" label="发布日期"></el-table-column>
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
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" placeholder="标题"></el-input>
        </el-form-item>
        <el-form-item label="简介" prop="descr">
          <el-input type="textarea" v-model="form.descr" placeholder="简介"></el-input>
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
        <el-form-item label="分类" prop="categoryId">
          <el-select v-model="form.cid" style="width: 100%">
            <el-option v-for="item in categoryList" :key="item.id" :value="item.id" :label="item.name"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="标签" prop="tags">
          <el-select v-model="tagsArr" multiple filterable allow-create default-first-option style="width: 100%">
            <el-option value="后端"></el-option>
            <el-option value="Java"></el-option>
            <el-option value="面试"></el-option>
            <el-option value="Vue"></el-option>
            <el-option value="前端"></el-option>
            <el-option value="大数据"></el-option>
            <el-option value="算法"></el-option>
            <el-option value="程序员"></el-option>
            <el-option value="小白"></el-option>
          </el-select>
        </el-form-item>
     <el-form-item label="内容" prop="content">
       <div id="editor2"></div>
        </el-form-item>
<!--        <textarea type="text" v-model="content">

        </textarea>-->
      </el-form>
<!--      <p>{{markdown}}</p>-->
      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
import E from "wangeditor"
import hljs from 'highlight.js'
import request from "@/utils/api";

import MarkdownIt from 'markdown-it'
export default {
  name: "Blog",
/* computed:{
   markdown(){
     const md=new MarkdownIt();
     const res=md.render(this.content)
     return res;
   }
 },*/
  data() {
    return {
     /* content:'',*/ // 输入的markdown
      user: JSON.parse(localStorage.getItem('user') || '{}'),
      tableData: [],  // 所有的数据
      //查询时需要的数据
      title: '',
      tags:'',
      brief:'',
      categoryName: '',
      username:'',
      userName: null,
      //富文本编辑器
      editor: null,
      //弹窗
      fromVisible: false,
      form: {
        id:0,
        title:'',
        content:'',
        descr:'',
        cover:'',
        tags:'',
        uid:0,
        date:'',
        read:0,
        cid:0,
      },
      rules: {
        title: [
          {required: true, message: '请输入标题', trigger: 'blur'},
        ],
      },
      ids: [],
      categoryList: [],
      tagsArr: [],
      pageSize: 4,
      currentPage: 1
    }
  },
  created() {
    if(this.user.authority==undefined||this.user.authority!='最高管理员'){
      this.$router.push("/")
      this.$message.error("权限不足！")
    }
    this.load()
  },
  methods: {
    s1(){
      request("/blog/s1/"+this.title).then(res=>{
        this.tableData=res;
      })
    },
    s2(){
      request("/blog/s2/"+this.tags).then(res=>{
        this.tableData=res;
      })
    },
    s3(){
      request("/blog/s3/"+this.brief).then(res=>{
        this.tableData=res;
      })
    },
    s4(){
      request("/blog/s4/"+this.categoryName).then(res=>{
        this.tableData=res;
      })
    },
    s5(){
      request("/blog/s5/"+this.username).then(res=>{
        this.tableData=res;
      })
    },
    clear(){
      this.form.uid=0
      this.form.cid=0
      this.form.title=''
      this.form.read=''
      this.form.tags=''
      this.form.content=''
      this.form.descr=''
      this.form.date=''
      this.form.cover=''
      this.form.id=0
    },
    getCurrentDate() {
      var now = new Date();
      var year = now.getFullYear(); //得到年份
      var month = now.getMonth(); //得到月份
      var date = now.getDate(); //得到日期
      month = month + 1;
      if (month < 10) month = "0" + month;
      if (date < 10) date = "0" + date;
      var str=year+'-'+month+'-'+date;
      return str;
    },
    //新增按钮
    handleAdd() {   // 新增数据
      this.clear()  // 新增数据的时候清空数据
      this.tagsArr = []
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
    },

    handleEdit(row) {   // 编辑数据
      this.form = JSON.parse(JSON.stringify(row))  // 给form对象赋值  注意要深拷贝数据
      this.tagsArr = JSON.parse(this.form.tags || '[]')
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
    //更新或者新增，用三目运算符进行
    save() {
      this.form.uid=this.user.id;
      this.form.date=this.getCurrentDate();
      //表单校验
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.form.tags = JSON.stringify(this.tagsArr)  // 把json数组转换成json字符串存到数据库
          this.form.content = this.editor.txt.html()
          request({
            url: this.form.id ? '/blog/update' : '/blog/add',
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
    //单删除功能
    del(id) {   // 单个删除
      this.$confirm('您确定删除吗？', '确认删除', {type: "warning"}).then(response => {
        request.get('/blog/del/' + id).then(res => {

            this.$message.success('操作成功')
            this.load()
        })
      }).catch(() => {
      })
    },

    //将数据加载上来
    load() {
      request.get('/blog/selectAll').then(res => {
        this.tableData = res
      })
      request.get('/cate/all').then(res => {
        this.categoryList = res|| []
      })
    },
    //重置按钮
    reset() {
      this.title = ''
      this.categoryName = ''
      this.tags=''
      this.brief=''
      this.username=''
      this.load()
    },

    //封面上传
    handleCoverSuccess(res) {
      this.form.cover = res.data
    },
    PageChange(val) {
      this.currentPage = val
    }
  }
}
</script>

<style scoped>
::v-deep .el-table .el-table__cell {
  z-index: auto;
}
</style>