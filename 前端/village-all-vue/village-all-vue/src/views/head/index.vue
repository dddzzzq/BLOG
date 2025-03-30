<script>
import {CirclePlus, Search} from '@element-plus/icons-vue'
import E from "wangeditor";
import hljs from "highlight.js";
import MarkdownIt from 'markdown-it'
import request from "@/utils/api";
import validCode from "@/components/ValidCode.vue";
export default {
  components: {
    validCode
  },
  computed: {
    CirclePlus() {
      return CirclePlus
    },
    Search() {
      return Search
    },
  },
  data(){
    return{
      searchInput:'',
      user:JSON.parse(localStorage.getItem('user') || '{}'),
      m1:false,
      m2:false,
      f:false,
      form:{},
      code:'',
      form1:{},
      rules: {
        title: [
          {required: true, message: '请输入标题', trigger: 'blur'},
        ],
      },
      categoryList: [],
      tagsArr: [],
      content:'',
      editor:null,
      log:false,
      reg:false,
      formlog:{},
      formreg:{},
    }
  },
  mounted() {

  },
  methods:{
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
    skip(){
      if(this.user==null||this.user=={}||this.user==undefined){
        this.$message.error("请登录！")
        this.$router.push("/")
        return
      }
      window.open("/usr?id="+this.user.id)
    },
    skip1(){
      if(this.user.id==undefined)
      {
        this.$message.error("请先登录！")
        return;
      }
      window.open("/cl")
    },
    ques(){
      if(this.searchInput==''||this.searchInput==null) return;
      window.open('/hall?q=' + this.searchInput)
      this.searchInput=''
    },
    submit(){
      if(this.user.id==undefined){
        this.$message.error("请登录!")
        this.$router.push("/")
        return
      }
      if(this.user.authority=='普通用户'){
        this.$message.error("权限不足，已提交申请至管理员")
        return
      }
      request.get('/cate/all').then(res => {
        this.categoryList = res|| []
      })
      this.f=true;
    },
    //封面上传
    handleCoverSuccess(res) {
      this.form.cover = res.data
    },
    handleCoverSuccess2(res) {
      this.form1.cover = res.data
    },
    handleAdd() {   // 新增数据
    //  this.clear()  // 新增数据的时候清空数据
      this.f = false;
      this.m1=true
      this.form={};
      this.tagsArr = []
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
    handleAdd1() {

      this.tagsArr = []
      this.f = false;
      this.m2=true
      this.form1={};
    },
    save2() {
      this.form1.uid=this.user.id;
      this.form1.date=this.getCurrentDate();
      //表单校验
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.form1.tags = JSON.stringify(this.tagsArr)  // 把json数组转换成json字符串存到数据库
          this.tagsArr=[]
          this.form1.content = new MarkdownIt().render(this.content)
          this.content=''
          console.log(this.form1)
          request({
            url: this.form1.id ? '/blog/update' : '/blog/add',
            method:'POST',
            data: this.form1
          }).then(res => {
            this.$message.success('保存成功')
            this.m2 = false
          })
        }
      })
    },
    save() {
      this.form.uid=this.user.id;
      this.form.date=this.getCurrentDate();

      //表单校验
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.form.tags = JSON.stringify(this.tagsArr)  // 把json数组转换成json字符串存到数据库
          this.tagsArr=[]
          this.form.content = this.editor.txt.html()
          request({
            url: this.form.id ? '/blog/update' : '/blog/add',
            method:'POST',
            data: this.form
          }).then(res => {
            this.$message.success('保存成功')
            this.m1 = false
          })
        }
      })
    },
    getCode(code){
      this.code=code;
    },
    test(){
      if(this.formlog.username==undefined||this.formlog.password==undefined||this.formlog.code==undefined){
        this.$message.error("请将信息填写完整！")
        return
      }
      if(this.code.toLowerCase()!=this.formlog.code.toLowerCase()){
        this.$message.error("验证码错误，请重试！")
        return
      }
      request.post("/judge",this.formlog).then(res=>{
        if(res.data==null) {
          this.$message.error("用户信息错误，请重试！")
          return;
        }
        if(res.code==200){
          this.$message.success("你好"+res.data.username+"!");
          localStorage.setItem("user",JSON.stringify(res.data));
          this.user=JSON.stringify(res.data)
          this.$router.go(0)
          this.log=false
          this.formlog={}
        }

      })

    },
    test1(){
      if(this.formreg.username==undefined||this.formreg.password==undefined||this.formreg.code==undefined||this.formreg.repassword==undefined){
        this.$message.error("请将信息填写完整！")
        return
      }
      if(this.formreg.password!=this.formreg.repassword){
        this.$message.error("两次密码输入不一致，请重试！")
        return
      }
      if(this.code.toLowerCase()!=this.formreg.code.toLowerCase()){
        this.$message.error("验证码错误，请重试！")
        return
      }
      this.formreg.time=this.getCurrentDate();
      this.formreg.authority="普通用户"
      request.post("/add",this.formreg).then(res=>{

        if(res.data==null) {
          this.$message.error("用户名重复，请更换用户名！")
          return;
        }
        if(res.code==200){
          this.$message.success("注册成功！");
          this.reg=false
          request.post("/judge",this.formreg).then(res=>{
            localStorage.setItem("user",JSON.stringify(res.data));
            this.user=JSON.stringify(res.data)
            this.$router.go(0)
            this.formreg={}
          })
        }
      })

    },
    quit(){
      localStorage.clear();
      this.$router.go(0);
    }
  }
}

</script>

<template>
  <div class="box-header">
    <div class="header-menu">
      <el-menu
          class="el-menu-demo"
          mode="horizontal"
          :ellipsis="false"
          router="true"
      >
        <div class="header-icon" ><img style="height: 70%" src="/src/assets/1.png" alt=""></div>
        <el-menu-item index="hall">博客</el-menu-item>
        <el-menu-item index="activity">活动中心</el-menu-item>
        <el-menu-item >学习</el-menu-item>
        <el-menu-item >社区</el-menu-item>
      </el-menu>
    </div>
    <div class="header-search">
      <el-input
          v-model="searchInput"
          style="width: 240px"
          placeholder="Please input"
      />
      <el-button
          type="primary"
          style="width: 100px;border-top-right-radius: 20px;border-bottom-right-radius: 20px;"
          :icon="Search"
          @click="ques"
      >
        搜索
      </el-button>
    </div>
    <div class="header-user">
      <el-dropdown>
        <span class="el-dropdown-link">
          <el-avatar
              :src="user.avatar"
          />
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="skip()" v-if="this.user.id!=undefined">个人中心</el-dropdown-item>
            <el-dropdown-item @click="quit()" v-if="this.user.id!=undefined">退出登录</el-dropdown-item>
            <el-dropdown-item @click="skip1()" v-if="user.authority=='最高管理员'&&user.token">管理后台</el-dropdown-item>
            <el-dropdown-item @click="log=true" v-if="this.user.id==undefined">登录</el-dropdown-item>
            <el-dropdown-item @click="reg=true;console.log(this.reg)" v-if="this.user.id==undefined">注册</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
    <div class="header-add">
      <el-button
          type="primary"
          style="height:40px;width: 100px;border-radius: 20px;font-size: 16px"
          :icon="CirclePlus"
          @click="submit"
      >
        发布
      </el-button>
    </div>
  </div>
  <router-view></router-view>
<!--=====================模式选择=============================================-->
  <el-dialog
      v-model="f"
      title="请选择发布模式"
      width="500"
  >

     <div style="margin-left: 80px">
       <el-button type="success" @click="handleAdd">富文本编辑器模式</el-button>
       <el-button type="primary" @click="handleAdd1">
         markdown编辑器模式
       </el-button>
     </div>
  </el-dialog>

  <el-dialog
      v-model="m1"
      title="富文本编辑器"
      width="800"
      :close-on-click-modal="false" destroy-on-close
  >`
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
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="m1=false">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </div>
  </el-dialog>

  <el-dialog
      v-model="m2"
      title="markdown编辑器"
      width="800"
      :close-on-click-modal="false" destroy-on-close
  >
    <el-form :model="form1" label-width="100px" style="padding-right: 50px" :rules="rules" ref="formRef">
      <el-form-item label="标题" prop="title">
        <el-input v-model="form1.title" placeholder="标题"></el-input>
      </el-form-item>
      <el-form-item label="简介" prop="descr">
        <el-input type="textarea" v-model="form1.descr" placeholder="简介"></el-input>
      </el-form-item>
      <el-form-item label="封面" prop="cover">
        <el-upload
            :action="'http://localhost:80/file/upload'"
            :headers="{ token: user.token }"
            list-type="picture"
            :on-success="handleCoverSuccess2"
        >
          <el-button type="primary">上传封面</el-button>
        </el-upload>
      </el-form-item>
      <el-form-item label="分类" prop="categoryId">
        <el-select v-model="form1.cid" style="width: 100%">
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
      <el-form-item label="内容" prop="categoryId">
      <textarea  type="text" style="width: 600px;height: 800px" v-model="content">
        </textarea>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="m2=false">取 消</el-button>
      <el-button type="primary" @click="save2">确 定</el-button>
    </div>
  </el-dialog>
<!--===========================登录弹窗==========================================-->
  <el-dialog
      v-model="log"
      title="登录"
      width="500"
      :close-on-click-modal="false" destroy-on-close
  >
    <el-form :model="formlog" >
      <el-form-item prop="username">
        <el-input suffix-icon="el-icon-User"  style="width: 100%" placeholder="请输入账号" v-model="formlog.username"></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input type="password" style="width: 100%" placeholder="请输入密码" v-model="formlog.password"></el-input>
      </el-form-item>
      <el-form-item prop="code">
        <div style="display: flex">
          <el-input  size="large" style="flex: 8;width: 95%;margin-top: 5px" placeholder="请输入验证码" v-model="formlog.code"></el-input>
          <div style="flex: 1;height: 36px;width: 80px;margin-left: 40px"  >

            <valid-code @update:value="getCode"></valid-code>
          </div>
        </div>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" style="flex: 1" @click="test">登录</el-button>
      </el-form-item>

    </el-form>
  </el-dialog>
  <el-dialog
      v-model="reg"
      title="注册"
      width="500"
      :close-on-click-modal="false" destroy-on-close
  >
    <el-form :model="formreg" >
      <el-form-item prop="username">
        <el-input suffix-icon="el-icon-User"  style="width: 100%" placeholder="请输入账号" v-model="formreg.username"></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input type="password" style="width: 100%" placeholder="请输入密码" v-model="formreg.password"></el-input>
      </el-form-item>
      <el-form-item prop="repassword">
        <el-input type="password" style="width: 100%" placeholder="请再次输入密码" v-model="formreg.repassword"></el-input>
      </el-form-item>
      <el-form-item prop="code">
        <div style="display: flex">
          <el-input  size="large" style="flex: 8;width: 95%;margin-top: 5px" placeholder="请输入验证码" v-model="formreg.code"></el-input>
          <div style="flex: 1;height: 36px;width: 80px;margin-left: 40px"  >
            <valid-code @update:value="getCode"></valid-code>
          </div>
        </div>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" style="flex: 1" @click="test1">注册</el-button>
      </el-form-item>

    </el-form>
  </el-dialog>
</template>

<style scoped>
.box-header {
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid #cccccc;
}
.header-icon {
  padding: 0 20px;
  display: flex;
  justify-content: center;
  align-items: center;
}
.el-menu {
  font-family: Arial;
  --el-menu-horizontal-height: 60px;
  --el-menu-horizontal-sub-item-height: 26px;
  --el-menu-item-font-size: 18px;
  --el-menu-text-color: #000000;
  --el-menu-hover-text-color: #097bfe;
  --el-menu-bg-color: #fff;
  --el-menu-hover-bg-color: #E9EFFF;
  --el-menu-active-color: #097bfe;
}
.el-menu--horizontal.el-menu {
   border-bottom: 0;
}
::v-deep .el-menu--horizontal>.el-menu-item.is-active {
  border-bottom: 3px solid #097bfe;
  color: #097bfe !important;
  background-color: #fff;
}
.header-search {
  display: flex;
  justify-content: center;
  align-items: center;
}
.el-input {
  height: 40px;
  font-size: 16px;
}
::v-deep .el-input__wrapper {
  border-top-left-radius: 20px;
  border-top-right-radius: 0;
  border-bottom-right-radius: 0;
  border-bottom-left-radius: 20px;
}
.header-search .el-button {
  align-items: center;
  -webkit-appearance: none;
  background-color: var(--el-button-bg-color);
  border: var(--el-border);
  border-color: var(--el-button-border-color);
  border-radius: 0;
  box-sizing: border-box;
  color: var(--el-button-text-color);
  cursor: pointer;
  display: inline-flex;
  font-size: 16px;
  font-weight: var(--el-button-font-weight);
  height: 40px;
  justify-content: center;
  line-height: 1;
  outline: none;
  padding: 8px 15px;
  text-align: center;
  transition: .1s;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
  vertical-align: middle;
  white-space: nowrap;
}
.el-dropdown-link {
  border: 0 !important;
}
.example-showcase .el-dropdown-link {
  cursor: pointer;
  color: var(--el-color-primary);
  display: flex;
  align-items: center;
}
.header-add {
  padding-right: 20px;
}
</style>
