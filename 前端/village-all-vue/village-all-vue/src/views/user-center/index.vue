<script setup>
import {nextTick, onMounted, ref, watch} from "vue";
import { Calendar, Top, StarFilled, Edit, Opportunity, Comment } from "@element-plus/icons-vue"
import ListItem from "@/views/user-center/components/list-item.vue";
import requset from "@/utils/api";
import { useRoute } from "vue-router";
import request from "@/utils/api";
import E from "wangeditor";
import hljs from "highlight.js";
const user1 = ref(JSON.parse(localStorage.getItem('user') || '{}'))
const user =ref({})
const route = useRoute();
const id=ref(route.query.id)
const load=()=>{
  requset.get("/jud2/"+id.value).then(res=>{
    user.value=res.data
    requset.get("/blog/sl/"+user.value.id).then(res=>{
      userAchievement.value[0].number=res;
    })
    requset.get("/blog/ss/"+user.value.id).then(res=>{
      userAchievement.value[1].number=res;
    })
    requset.get("/blog/deli/"+user.value.id).then(res=>{
      userAchievement.value[2].number=res;
    })
    requset.get("/blog/fuid/"+user.value.id).then(res=>{
      for(let i=0;i<res.length;i++){
        publish.value[i]=res[i]
        publish.value[i].tags=JSON.parse(res[i].tags||[])
      }
    })
    requset.get("/blog/myd/"+user.value.id).then(res=>{
      for(let i=0;i<res.length;i++){
        collections.value[i]=res[i]
        collections.value[i].tags=JSON.parse(res[i].tags||'{}')
      }
    })
  })
}
onMounted(()=>{
  load()
})
const tagsArr=ref([])
let editor=null
const categoryList=ref([])
const change=(item)=>{

  request.get('/cate/all').then(res => {
    categoryList.value = res|| []
  })
  form.value=JSON.parse(JSON.stringify(item))
  tagsArr.value = form.value.tags || '[]'
  fromVisible.value = true   // 打开弹窗
  nextTick(() => {
    editor=new E('#editor2');
    //设置上传图片
    editor.config.uploadImgServer='http://localhost:80/file/editor/upload';
    editor.config.uploadFileName='file';
    editor.config.uploadImgHeaders={
      token:user1.value.token
    }

    //设置上传视频
    editor.config.uploadVideoServer='http://localhost:80/file/editor/videoupload';
    editor.config.uploadVideoName='file';
    editor.config.uploadVideoHeaders={
      token:user1.value.token
    }
    //配置高亮
    editor.highlight=hljs;
    editor.create();
    });

  setTimeout(() => {
    editor.txt.html(form.value.content)
  }, 0)
}
const del=(id)=>{
  request.get('/blog/del/' + id).then(res => {
    location.reload()
  })
}
const pre=(id)=>{
  window.open("/detail?blogId="+id)
}
const form=ref({})
const userSetDuration = (time) => {
  const today = new Date()
  const year = today.getFullYear()
  const month = today.getMonth() + 1 // 月份是从 0 开始计数的，需要加1
  const day = today.getDate()
  const currentDate = year + "-" +
      (month < 10 ? "0" : "") + month + "-" +
      (day < 10 ? "0" : "") + day
  const Start = new Date(time)
  const End = new Date(currentDate)
  const Duration =  (End - Start) / (1000 * 60 * 60 * 24);
  return Duration;
}
const userAchievement = ref([
  {
    name: '总点赞',
    number: 0,
  },
  {
    name: '总收藏',
    number: 0,
  },
  {
    name: '总发布',
    number: 0
  }
])

const publish = ref([])
const collections = ref([])
const likes = ref([
  {
    title: '算法板子汇总（持续更新中（（（',
    descr: '算法笔记还会持续更新, 还有诸多代办，不过不用着急，作者还有很长的花期，半年内还会学很多算法嘟，一定会完成的^ _ ^😋😋',
    content: '',
    cover: 'src/assets/VCG211300614411.jpg',
    tags: ["算法", "板子"],
    date: '2023-12-09',
    star: 30,
    likeCount: 100,
    read: 999,
  },
])

const changeInfoVisible = ref(false)
const fromVisible=ref(false)
const changeInfo = () => {
  request.post('/update',user.value).then(res=>{
    if(res.code==='200'){
      localStorage.setItem("user",JSON.stringify(user.value));
      load()
      changeInfoVisible.value= false
    }
  })
}
const handleAvatarSuccess=(response, file, fileList)=>{
    user.value.avatar=response.data
}
const save=()=>{
     form.value.tags = JSON.stringify(tagsArr.value)  // 把json数组转换成json字符串存到数据库
      form.value.content = editor.txt.html()
      request({
        url: form.value.id ? '/blog/update' : '/blog/add',
        method:'POST',
        data: form.value
      }).then(res => {
        load()
        fromVisible.value = false
      })
}
const handleCoverSuccess=(res)=> {
  form.value.cover = res.data
}
var token=JSON.parse(localStorage.getItem("user") || '{}').token
const myHeaders=ref({
  token: token
})
</script>

<template>
  <div class="user-center">
    <div class="user-bgc"></div>
    <div class="user-all">
      <div class="user-information">
        <div class="user-img">
          <el-image
              style="width: 140px; height: 140px; border-radius: 50%"
              :src="user.avatar"
              :zoom-rate="1.2"
              :max-scale="7"
              :min-scale="0.2"
              :preview-src-list="[user.avatar]"
              :initial-index="4"
              fit="cover"
          />
        </div>
        <div class="user-inf">
          <div class="row-1">
            <div style="display: flex; align-items: center">
              <div class="user-name">
                {{ user.username }}
              </div>
              <div class="user-sex">
                <img src="/src/assets/man.png" alt="" v-if="user.sex == '男'" style="height: 30px">
                <img src="/src/assets/women.png" alt="" v-else style="height: 30px">
              </div>
            </div>
            <div class="user-achievement">
              <div class="achievement-item" v-for="item in userAchievement">
                <div class="achievement-item-name">
                  {{ item.name }}
                </div>
                <div class="achievement-item-number">
                  {{ item.number }}
                </div>
              </div>
            </div>
          </div>
          <div class="row-2">
            <div class="tip">
              <div class="user-school">
                <img src="/src/assets/school.png" alt="" style="height: 25px">
                <div style="margin-left: 5px">{{ user.school }}</div>
              </div>
              <div class="user-place">
                <img src="/src/assets/place.png" alt="" style="height: 20px">
                <div style="margin-left: 5px">{{ user.place }}</div>
              </div>
              <div class="user-time">
                <img src="/src/assets/time.png" alt="" style="height: 20px">
                <div style="margin-left: 5px">
                  <span v-if="userSetDuration(user.time) >= 365">
                    {{ Math.floor(userSetDuration(user.time) / 365) }} 年
                  </span>
                  <span v-else>{{ userSetDuration(user.time) }} 天</span>
                </div>
              </div>
            </div>
            <div class="set-up">
              <el-button
                  type="primary"
                  round
                  @click="changeInfoVisible = true"
                  v-if="user.id==user1.id"
              >
                编辑资料
              </el-button>
            </div>
          </div>
          <div class="row-3">
            <div class="user-info">
              <el-icon><Edit /></el-icon>
              <span style="margin-left: 10px">{{ user.info }}</span>
            </div>
          </div>
        </div>
      </div>
      <div class="user-main">
        <div class="issue">
          <el-tabs type="border-card" class="demo-tabs" :stretch="true">
            <el-tab-pane>
              <template #label>
                <span class="custom-tabs-label">
                  <el-icon><Top /></el-icon>
                  <span v-if="user.id==user1.id">你的发布</span>
                  <span v-else>ta的发布</span>
                </span>
              </template>
              <div class="publish">
                <div class="publish-item" v-for="item in publish">
                  <list-item :item="item"/>
                  <el-tooltip class="item" effect="dark" content="点击后，博客将会被删除" placement="top">
                    <el-button type="error" v-if="user.id==user1.id" @click="del(item.id)">删除 </el-button>
                  </el-tooltip>
                  <el-button type="success" v-if="user.id==user1.id"  @click="change(item)">修改</el-button>
                  <el-button type="error" v-if="user.id==user1.id" @click="pre(item.id)">预览</el-button>
                </div>
              </div>
            </el-tab-pane>
            <el-tab-pane>
              <template #label>
                <span class="custom-tabs-label">
                  <el-icon><StarFilled /></el-icon>
                  <span  v-if="user.id==user1.id">你的收藏</span>
                  <span v-else>ta的收藏</span>
                </span>
              </template>
              <div class="collection">
                <div class="collection-item" v-for="item in collections">
                  <list-item :item="item" />
                </div>
              </div>
            </el-tab-pane>
            <el-tab-pane>
              <template #label>
                <span class="custom-tabs-label">
                  <el-icon><Opportunity /></el-icon>
                  <span>你的点赞</span>
                </span>
              </template>
              <div class="like">
                <div class="like-item" v-for="item in likes">
                  <list-item :item="item" />
                </div>
              </div>
            </el-tab-pane>
            <el-tab-pane>
              <template #label>
                <span class="custom-tabs-label">
                  <el-icon><Comment /></el-icon>
                  <span v-if="user.id==user1.id">你的评论</span>
                  <span v-else>ta的评论</span>
                </span>
              </template>
              <div class="comment">
                <div class="comment-item" >

                </div>
              </div>
            </el-tab-pane>
          </el-tabs>
        </div>
      </div>
    </div>
  </div>
  <el-dialog
      v-model="changeInfoVisible"
      title="编辑资料"
      width="600"
  >
    <div class="change-user">
      <el-form :model="user" label-width="auto" style="width: 500px;">
        <el-form-item>
          <el-upload
              class="avatar-uploader"
              action="http://localhost:80/file/upload"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :headers="myHeaders"
          >
            <img v-if="user.avatar" :src="user.avatar" class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="user.name" />
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="user.sex">
            <el-option label="男" value="男" />
            <el-option label="女" value="女" />
          </el-select>
        </el-form-item>
        <el-form-item label="学校">
          <el-input v-model="user.school" />
        </el-form-item>
        <el-form-item label="籍贯">
          <el-input v-model="user.place" />
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="user.phone" />
        </el-form-item>
        <el-form-item label="个性签名">
          <el-input
              v-model="user.info"
              :rows="2"
              type="textarea"
              style="height: 100px" />
        </el-form-item>
      </el-form>
    </div>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="changeInfoVisible = false;load()">取消</el-button>
        <el-button type="primary" @click="changeInfo">
          确定
        </el-button>
      </div>
    </template>
  </el-dialog>

  <el-dialog title="信息" v-model="fromVisible" width="60%" :close-on-click-modal="false" destroy-on-close>
    <el-form :model="form" label-width="100px" style="padding-right: 50px"  ref="formRef">
      <el-form-item label="标题" prop="title">
        <el-input v-model="form.title" placeholder="标题"></el-input>
      </el-form-item>
      <el-form-item label="简介" prop="descr">
        <el-input type="textarea" v-model="form.descr" placeholder="简介"></el-input>
      </el-form-item>
      <el-form-item label="封面" prop="cover">
        <el-upload
            :action="'http://localhost:80/file/upload'"
            :headers="{ token: user1.token }"
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
      <el-form-item label="内容" prop="content" >
        <div id="editor2" style="width: 100%"></div>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="fromVisible = false">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </div>
  </el-dialog>
</template>

<style scoped>
.user-center {
  background-image: url('src/assets/VCG211378794729.jpg');
  background-size: 100%;
  background-repeat: no-repeat;
  display: flex;
  justify-content: center;
  position: relative;
}
.user-bgc {
  position: absolute;
  width: 100%;
  background: linear-gradient(180deg, hsla(0, 0%, 98%, 0), hsla(0, 0%, 98%, .95) 85%, #ffffff);;
  height: 900px;
  left: 0;
  z-index: 0;
}
.user-all {
  margin: 80px 0 0;
  min-width: 1200px;
  z-index: 2;
}
.user-information {
  padding: 30px;
  position: relative;
  display: flex;
}
.user-img {
  position: absolute;
  top: -1px;
  left: 100px;
  padding: 5px;
  background-color: #eeeeee;
  border-radius: 50%;
}
.user-inf {
  height: 240px;
  min-width: 1200px;
  background: rgba(255, 255, 255, 0.8);
  border-radius: 10px;
}
.row-1 {
  margin-top: 20px;
  display: flex;
  justify-content: space-between;
}
.user-name {
  font-size: 40px;
  margin-left: 280px;
}
.user-sex {
  margin-left: 20px;
}
.user-achievement {
  margin: 0 50px;
  display: flex;
  justify-content: space-between;
  width: 30%;
}
.achievement-item {
  text-align: center;
}
.achievement-item-name {
  font-size: 22px;
}
.achievement-item-number {
  margin-top: 10px;
  font-size: 30px;
  font-family: Arial;
}
.row-2 {
  margin-top: 50px;
  margin-left: 50px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: calc(100% - 80px);
}
.tip {
  width:45%;
  display: flex;
  //justify-content: space-between;
  align-items: center;
}
.user-school,
.user-place,
.user-time {
  margin-right: 10px;
  padding: 0 20px;
  border-radius: 18px;
  height: 36px;
  background-color: #f1f1f1;
  font-size: 15px;
  display: flex;
  align-items: center;
  color: #707070;
}
.el-button {
  --el-button-bg-color: #fff;
  --el-button-border-color: #097bfe;
  --el-button-text-color: #097bfe;
  --el-button-active-color: #097bfe;
  --el-button-hover-text-color: var(--el-color-white);
  --el-button-hover-link-text-color: var(--el-color-primary-light-5);
  --el-button-hover-bg-color: #097bfe;
  --el-button-hover-border-color: #097bfe;
  --el-button-active-bg-color: #097bfe;
  --el-button-active-border-color: #097bfe;
  font-size: 20px;
  height: 36px;
}
.row-3 {
  margin-top: 10px;
  margin-left: 60px;
  font-size: 20px;
  color: #707070;
  display: flex;
  align-items: center;
}
.user-main {
  min-width: 1200px;
  padding: 0 30px;
}
.el-tabs {
  --el-tabs-header-height: 60px;
  border-radius: 10px;
}
.el-tabs--border-card {
  background: var(--el-bg-color-overlay);
  //border: 1px solid #097bfe;

}
::v-deep .el-tabs--border-card>.el-tabs__header {
  background-color: #ffffff;
  border-radius: 10px 10px 0 0;
  text-align: center;
  //border: 1px solid #097bfe;
}
::v-deep .el-tabs--border-card>.el-tabs__header .el-tabs__item {
  font-size: 20px;
  font-family: Arial, 黑体;
  color: #000000;
}
::v-deep .el-tabs--border-card>.el-tabs__header .el-tabs__item:not(.is-disabled):hover {
  color: #097bfe;
}
::v-deep .el-tabs--border-card>.el-tabs__header .el-tabs__item.is-active:hover {
  //color: #ffffff;
}
::v-deep .el-tabs--border-card>.el-tabs__header .el-tabs__item.is-active {
  background-color: rgba(9, 123, 254, 0.1);
  //border-left-color: #097bfe;
  //border-right-color: #097bfe;
  //border-top-color: #097bfe;
  color: #097bfe;
  border-radius: 10px 10px 0 0;
  font-size: 25px;
}
::v-deep .el-tabs--border-card>.el-tabs__content {
  min-height: 500px;
}
.custom-tabs-label {
  display: flex;
  align-items: center;
  span {
    margin-left: 10px;
  }
}
.publish-item,
.collection-item,
.like-item,
.comment-item {
  margin-top: 10px;
  padding-bottom: 10px;
  border-bottom: 1px solid #cccccc;
  width: 100%;
}
.change-user {
  display: flex;
  justify-content: center;
  width: 100%;
}
.el-form {
  --el-form-label-font-size: 18px;
  --el-form-inline-content-width: 220px;
}
::v-deep .el-form-item__content {
 justify-content: center;
}
.el-input {
  --el-input-text-color: #000000;
  --el-input-focus-border: #097bfe;
  --el-input-focus-border-color: #097bfe;
  --el-input-height: 40px;
  font-size: 18px;
  line-height: var(--el-input-height);
}
::v-deep .el-select__wrapper {
  font-size: 18px;
  line-height: 40px;
  min-height: 40px;
}
::v-deep .el-select__placeholder {
  color: #000000;
}
.el-select-dropdown__item {
  color: #000000;
  font-size: 18px;
  height: 36px;
}
.el-select-dropdown__item.is-selected {
  color: #097bfe;
  font-weight: bold;
}
::v-deep .el-textarea__inner {
  color: #000000;
  font-family: Arial;
  font-size: 18px;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
  border-radius: 50%;
}
</style>
