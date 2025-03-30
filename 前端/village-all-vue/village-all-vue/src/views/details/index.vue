<!--博客详情页面-->
<template>
  <div class="main-content">
    <div style="display: flex; grid-gap: 10px">

      <div style="flex: 1">
        <el-card class="card" style="padding: 30px; margin-bottom: 10px;margin-left: 120px;margin-right: 10px">
          <div style="font-weight: bold; font-size: 24px; margin-bottom: 20px">{{ blog.title }}</div>
          <div style="color: #666; margin-bottom: 20px">
            <span style="margin-right: 20px">
              <el-icon><UserFilled /></el-icon>
              {{ blog.userName }}</span>
            <span style="margin-right: 20px">
             <el-icon><Calendar /></el-icon>
              {{ blog.date }}</span>
            <span style="margin-right: 20px"><el-icon><View /></el-icon> {{ blog.readCount }}</span>
            <span>
              <el-tag v-for="item in tagsArr" :key="item" type="primary" style="margin-right:5px">{{ item }}</el-tag>
            </span>
            <hr>
          </div>

          <div class="w-e-text">
            <div v-html="blog.content"></div>
          </div>
        </el-card>
        <!--     点赞和收藏数据   -->
        <el-card class="card" style="text-align: center; font-size: 20px;margin-right: 10px; color: #666; margin-bottom: 10px;margin-left: 120px">
          <span style="margin-right: 20px; cursor: pointer;" @click="setLikes" v-if="likeflag"><el-icon style="color: #097bfe"><Opportunity /></el-icon> {{ blog.likeCount}}</span>
          <span style="margin-right: 20px; cursor: pointer;" @click="setLikes" v-else><el-icon ><Opportunity /></el-icon> {{ blog.likeCount}}</span>
          <span style=" cursor: pointer"  @click="setCollect" v-if="starflag" ><el-icon style="color: yellow"><StarFilled /></el-icon> {{ blog.star }}</span>
          <span style=" cursor: pointer"  @click="setCollect" v-else ><el-icon><StarFilled /></el-icon> {{ blog.star }}</span>
        </el-card>

        <!-- ========================= 评论========================================= -->
        <el-card style="margin-left:120px;margin-right: 10px">
          <h2 style="margin-bottom: 20px">评论 {{ commentCount }}</h2>

          <div style="margin-bottom: 20px">
            <el-input type="textarea" placeholder="请输入评论内容" v-model="commentContent"></el-input>
            <div style="text-align: right; margin-top: 5px">
              <el-button type="primary" @click="addComment">评 论</el-button>
            </div>
          </div>

          <div>
            <div style="display: flex; grid-gap: 20px; margin-bottom: 20px" v-for="item in commentList" :key="item.id">
              <img :src="item.avatar" alt="" style="width: 50px; height: 50px; border-radius: 50%">
              <div style="flex: 1">
                <!--==============================第一级评论===========================-->
                <div style="margin-bottom: 10px">
                  <div style="color: #666; margin-bottom: 10px">{{ item.userName }}</div>
                  <div style="color: #444; margin-bottom: 10px">{{ item.content }}</div>
                  <div style="color: #888; font-size: 13px; margin-bottom: 10px"><span style="margin-right: 20px">{{ item.time }}</span>
                    <span style="cursor: pointer;" :class="{ 'comment-active' : item.showReplyInput }" @click="handleShowReplyInput(item)"><el-icon><ChatLineRound /></el-icon>评论</span>
                    <span style="margin-left: 20px; cursor: pointer" @click="del(item.id)" v-if="item.uid === user1.id"><el-icon><Delete /></el-icon>删除</span>
                  </div>
                  <div v-if="item.showReplyInput">
                    <el-input type="textarea" placeholder="请输入回复内容" v-model="item.replyContent"></el-input>
                    <div style="text-align: right; margin-top: 5px">
                      <el-button type="primary" @click="addReplay(item)">回 复</el-button>
                    </div>
                  </div>
                </div>
                <!--=============================回复============================================-->
                <div style="display: flex;  grid-gap: 20px; margin-bottom: 20px" v-for="sub in item.children" :key="sub.id">
                  <img :src="sub.avatar" alt="" style="width: 50px; height: 50px; border-radius: 50%">
                  <div style="flex: 1">
                    <div style="color: #666; margin-bottom: 10px">{{ sub.userName }} <span style="color: #333"  v-if="sub.replyUser != sub.userName">回复 @{{ sub.replyUser }}</span></div>
                    <div style="color: #444; margin-bottom: 10px">{{ sub.content }}</div>
                    <div style="color: #888; font-size: 13px; margin-bottom: 10px"><span style="margin-right: 20px">{{ sub.time }}</span>
                      <span style="cursor: pointer;" :class="{ 'comment-active' : sub.showReplyInput }" @click="handleShowReplyInput(sub)"><el-icon><ChatLineRound /></el-icon>评论</span>
                      <span style="margin-left: 20px; cursor: pointer" @click="del(sub.id)" v-if="sub.uid === user1.id"><el-icon><Delete/></el-icon>删除</span>
                    </div>
                    <div v-if="sub.showReplyInput">
                      <el-input type="textarea" placeholder="请输入回复内容" v-model="sub.replyContent"></el-input>
                      <div style="text-align: right; margin-top: 5px">
                        <el-button type="primary" @click="addReplay(sub)">回 复</el-button>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </el-card>
        <!--=======================  评论 =================================== -->
      </div>

      <div style="width: 260px;margin-right: 60px" >
        <el-card class="card" style="margin-bottom: 10px">
          <div style="display: flex; align-items: center; grid-gap: 10px; margin-bottom: 10px" @click="skip">
            <img :src="user?.avatar" alt="" style="width: 50px; height: 50px; border-radius: 50%">
            <div style="flex: 1;">
              <div style="font-weight: bold; margin-bottom: 5px" >{{ user?.username }}</div>
              <div style="color: #666; font-size: 13px" class="line2">{{ user?.info }}</div>
            </div>
          </div>

          <div style="display: flex">
            <div style="flex: 1; text-align: center">
              <div style="margin-bottom: 5px">文章</div>
              <div style="color: #888">{{this.per.blnum}}</div>
            </div>
            <div style="flex: 1; text-align: center">
              <div style="margin-bottom: 5px">点赞</div>
              <div style="color: #888">{{ this.per.likenum }}</div>
            </div>
            <div style="flex: 1; text-align: center">
              <div style="margin-bottom: 5px">收藏</div>
              <div style="color: #888">{{this.per.starnum}}</div>
            </div>
          </div>
        </el-card>
        <el-card class="card" style="margin-bottom: 10px">
          <div style="font-weight: bold; font-size: 20px; padding-bottom: 10px; border-bottom: 1px solid #ddd; margin-bottom: 10px">相关推荐</div>
          <div>
            <div style="margin-bottom: 15px" v-for="item in recommendList" :key="item.id">
              <a :href="'/detail?blogId=' + item.id" target="_blank"><div class="recommend-title line2">{{ item.title }}</div></a>
              <div style="color: #888">
                <span>阅读</span> <span>{{ item.readCount }}</span>
                <span style="margin-left: 10px">点赞</span> <span>{{ item.likeCount }}</span>
                <span style="margin-left: 10px">收藏</span> <span>{{ item.star }}</span>
              </div>
            </div>
          </div>
        </el-card>
      </div>
    </div>
  </div>
</template>

<script>
import request from "@/utils/api";
import { Calendar,UserFilled,View,Opportunity,StarFilled,ChatLineRound,Delete } from '@element-plus/icons-vue'
import requset from "@/utils/api";
export default {
  name: "BlogDetail",

  data() {
    return {
      blogId: this.$route.query.blogId,
      blog: {},
      user:{},
      tagsArr: [],
      per:{
        blnum:0,
        likenum:0,
        starnum:0,
      },
      //推荐算法list集合
      recommendList: [],
      likeflag:false,
      starflag:false,
      commentCount: 0,
      commentContent: '',
      commentList:[],
      user1:JSON.parse(localStorage.getItem('user') || '{}'),
          }
  },
  created() {
    this.load()
    this.loadComment();
  },
  methods: {
    del(id) {   // 单个删除
      this.$confirm('您确定删除吗？', '确认删除', {type: "warning"}).then(res => {
        request.get('/com/del/' + id).then(res => {
            this.$message.success('操作成功')
            this.loadComment()
        })
      }).catch(() => {
      })
    },
    //发送回复的数据
    addReplay(item) {
      if(this.user1.username==undefined||this.user1.username==null||this.user1.username==''){
        this.$router.push("/")
        this.$message.error("请登录！")
        return
      }
      request.post('/com/ins', { pid: item.id, rid: item.rid, content: item.replyContent, fid: this.blogId, module: "博客",time: this.getCurrentDate()}).then(res => {
          this.$message.success('操作成功')
          item.replyContent = ''
          this.loadComment()  // 重新加载数据
      })
    },
    //在评论中评论,打开评论框
    handleShowReplyInput:(item)=> {
      item.showReplyInput=!item.showReplyInput
    },
    //添加评论
    addComment() {
      if(this.user1.username==undefined||this.user1.username==null||this.user1.username==''){
        this.$router.push("/")
        this.$message.error("请登录！")
        return
      }
      request.post('/com/ins', { content: this.commentContent, fid: this.blogId, module: '博客',time: this.getCurrentDate()}).then(res => {
          this.$message.success('评论成功')
          this.commentContent = ''
          this.loadComment()  // 重新加载数据

      })
    },
    //加载评论
    loadComment() {
      request.post('/com/selectForUser', 
         {  fid: this.blogId, module: '博客' }
      ).then(res => {
        this.commentList = res|| []
        this.commentCount=res.length

      })
    },
    setCollect(){
      if(this.user1.username==undefined||this.user1.username==null||this.user1.username==''){
        this.$router.push("/")
        this.$message.error("请登录！")
        return
      }
      request.post('/collect/set', {  fid: this.blogId, module: '博客' }).then(res => {
        this.load()  // 重新加载数据
      })
    },
    setLikes() {
      if(this.user1.username==undefined||this.user1.username==null||this.user1.username==''){
        this.$router.push("/")
        this.$message.error("请登录！")
        return
      }
      request.post('/like/set', {  fid: this.blogId, module: '博客' }).then(res => {
          this.load()  // 重新加载数据
      })
    },
    load() {
      request.get('/blog/s6/' + this.blogId).then(res => {
        this.blog = res || {}
        this.tagsArr = JSON.parse(this.blog.tags || '[]')
        request.get("/jud2/"+this.blog.uid).then(res=>{
          this.user=res.data||[];
          requset.get("/blog/sl/"+this.user.id).then(res=>{
            this.per.likenum=res;
          })
          requset.get("/blog/ss/"+this.user.id).then(res=>{
           this.per.starnum =res;
          })
          requset.get("/blog/deli/"+this.user.id).then(res=>{
            this.per.blnum=res;
          })
        })
      })
      //推荐算法
      request.get('/blog/selectRecommend/' + this.blogId).then(res => {
        this.recommendList = res || []
      })
      if(this.user1.username==undefined||this.user1.username==null||this.user1.username==''){
        this.starflag=false;
        this.likeflag=false
        return
      }

      request.get("/like/s1/"+this.blogId).then(res=>{
        this.likeflag=res
      })
      request.get("/collect/s1/"+this.blogId).then(res=>{
        this.starflag=res
      })

    },
    getCurrentDate() {
      var now = new Date();
      var year = now.getFullYear(); //得到年份
      var month = now.getMonth(); //得到月份
      var date = now.getDate(); //得到日期
      month = month + 1;
      if (month < 10) month = "0" + month;
      if (date < 10) date = "0" + date;
      var str = year + '-' + month + '-' + date;
      return str;
    },
    skip(){
      this.$router.push("/usr?id="+this.user.id)
    }
  }
}
</script>

<style>
/* blockquote 样式 */
blockquote {
  display: block;
  border-left: 8px solid #d0e5f2;
  padding: 20px 10px;
  margin: 10px 0;
  line-height: 1.4;
  font-size: 100%;
  background-color: #f1f1f1;
}

/* code 样式 */
code {
  display: inline-block;
  *display: inline;
  *zoom: 1;
  background-color: #f1f1f1;
  border-radius: 3px;
  padding: 3px 5px;
  margin: 0 3px;
}
pre code {
  display: block;
}
p {
  line-height: 30px
}
.line2 {
  word-break: break-all;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2; /* 超出几行省略 */
  overflow: hidden;
}

.active {
  color: orange !important;
}
.recommend-title {
  margin-bottom: 5px;
}
.recommend-title:hover {
  color: #2a60c9;
}
a {
  text-decoration: none;
  color: #000000;
}

</style>