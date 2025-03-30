<template>
  <div class="main-content">
    <el-card class="card" style="margin-bottom: 10px;margin-left: 130px;margin-right: 130px">
      <div style="display: flex; grid-gap: 20px">
        <img :src="activity.cover" alt="" style="width: 30%; height: 250px; border-radius: 5px">

        <div style="flex: 1;">
          <div style="font-weight: bold; font-size: 24px; margin-bottom: 10px">{{ activity.name }}</div>
          <div style="color: #666; font-size: 13px; line-height: 22px; text-align: justify; margin-bottom: 10px">{{ activity.descr }}</div>
          <div style="color: #666; margin-bottom: 10px"><el-icon><Calendar /></el-icon> 时间 <span style="margin-left: 5px">{{ activity.start }} ~ {{ activity.end }}</span></div>
          <div style="color: #666; margin-bottom: 10px"><el-icon><Position /></el-icon> 地址
            <span style="margin-left: 5px" >{{ activity.address }}</span>
            <el-tag style="margin-left: 5px" type="primary" size="mini">{{ activity.form }}</el-tag>
          </div>
          <div style="display: flex; align-items: center">
            <el-button type="primary" size="medium" @click="join" v-if="this.fl">已 报 名</el-button>
            <el-button type="primary" size="medium" @click="join" v-else>报 名</el-button>
            <div style="margin-left: 20px ;flex: 1">
              <el-button type="warning" @click=" setCollect" v-if="this.starflag" >已 收 藏</el-button>
              <el-button type="warning" @click=" setCollect" v-else>收藏</el-button>
            </div>
          </div>
        </div>
      </div>
    </el-card>

    <el-card  style="margin-bottom: 10px;margin-left: 130px;margin-right: 130px">
      <div style="font-weight: bold; font-size: 24px; margin-bottom: 20px; padding-left: 10px; border-left: 5px solid #2a60c9">活动详情</div>
      <div class="w-e-text">
        <div v-html="activity.content"></div>
      </div>
    </el-card>
<!--===========================评论====================================-->
    <el-card style="margin-left:130px;margin-right: 130px">
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
                <span style="margin-left: 20px; cursor: pointer" @click="del(item.id)" v-if="item.uid === user.id"><el-icon><Delete /></el-icon>删除</span>
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
                  <span style="margin-left: 20px; cursor: pointer" @click="del(sub.id)" v-if="sub.uid === user.id"><el-icon><Delete/></el-icon>删除</span>
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
<!--===========================评论====================================-->

  </div>
</template>

<script>

import request from "@/utils/api";
import { Calendar,Position} from "@element-plus/icons-vue"
export default {
  name: "ActivityDetail",
  data() {
    return {
      starflag:false,
      activity: {},
      activityId: this.$route.query.activityId,
      commentCount: 0,
      commentContent: '',
      commentList:[],
      fl:false,
      user:JSON.parse(localStorage.getItem('user') || '{}'),
    }
  },
  created() {
    this.load()
    this.loadComment()
  },
  methods: {
    loadJoin(){
      request.get("/sign/f/"+this.activityId).then(res=>{
        this.fl=res
      })
    },
    join(){
      if(this.user.name==''||this.user.name==undefined||this.user.name==null){
        this.$message.error("请先进行实名认证！")
        return
      }
      request.post("/sign/set",{
        time:this.getCurrentDate(),
        activityId:this.activityId,
      }).then(res=>{
        this.loadJoin();
      })

    },
    setCollect(){
      if(this.user.username==undefined||this.user.username==null||this.user.username==''){
        this.$router.push("/")
        this.$message.error("请登录！")
        return
      }
      request.post('/collect/set2', {  fid: this.activityId, module: '活动' }).then(res => {
        this.load()  // 重新加载数据
      })
    },
    load() {
      request.get('/act/s1/' + this.activityId).then(res => {
        this.activity = res || {}
      })
      if(this.user.username==undefined||this.user.username==null||this.user.username==''){
        this.starflag=false;
        return
      }
      request.get("/collect/s2/"+this.activityId).then(res=>{
        this.starflag=res
      })
    },
    loadComment() {
      request.post('/com/selectForUser2',
          {  fid: this.activityId, module: '活动' }
      ).then(res => {
        this.commentList = res|| []
        this.commentCount=res.length
      })
    },
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
      if(this.user.username==undefined||this.user.username==null||this.user.username==''){
        this.$router.push("/")
        this.$message.error("请登录！")
        return
      }
      request.post('/com/ins', { pid: item.id, rid: item.rid, content: item.replyContent, fid: this.activityId, module: "活动",time: this.getCurrentDate()}).then(res => {
        this.$message.success('操作成功')
        item.replyContent = ''
        this.loadComment()  // 重新加载数据
      })
    },
    //添加评论
    addComment() {
      if(this.user.username==undefined||this.user.username==null||this.user.username==''){
        this.$router.push("/")
        this.$message.error("请登录！")
        return
      }
      request.post('/com/ins', { content: this.commentContent, fid: this.activityId, module: '活动',time: this.getCurrentDate()}).then(res => {
        this.$message.success('评论成功')
        this.commentContent = ''
        this.loadComment()  // 重新加载数据
      })
    },
    handleShowReplyInput:(item)=> {
      item.showReplyInput=!item.showReplyInput
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
  }
}
</script>

<style scoped>

</style>