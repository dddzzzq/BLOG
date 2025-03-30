<!--前台主页-->
<template>
  <div class="main-content" >
    <div style="display: flex; align-items: flex-start; grid-gap: 10px;margin-top: 30px">

      <el-card style="width: 150px" class="card" v-if="this.q==''">
        <div class="category-item" :class="{ 'category-item-active': item.name === current }"
             v-for="item in categoryList" :key="item.id" @click="selectCategory(item.name)">{{ item.name }}</div>
      </el-card>

      <el-card style="flex: 1;margin-left: 20px" class="card" >

        <div class="blog-box" v-for="item in tableData" :key="item.id" v-if="total > 0" @click="skip(item.id)">
          <div style="flex: 1; width: 0">
            <div style="font-size: 16px; font-weight: bold; margin-bottom: 10px">{{ item.title }}</div>
            <div class="line1" style="color: #666; margin-bottom: 10px; font-size: 13px">{{ item.descr }}</div>
            <div style="display: flex">
              <div style="flex: 1; font-size: 13px">
                <span style="color: #666; margin-right: 20px"> <el-icon><UserFilled /></el-icon> {{ item.userName }}</span>
                <span style="color: #666; margin-right: 20px"><el-icon><View /></el-icon> {{ item.readCount }}</span>
                <span style="color: #666"><el-icon><Opportunity /></el-icon> {{ item.likeCount }}</span>
              </div>
              <div style="width: fit-content">
                <el-tag
                    type="primary"
                    v-for="tag in  item.tags"
                    style="margin-left: 4px"
                >
                  {{ tag }}
                </el-tag>
              </div>
            </div>
          </div>
          <div style="width: 150px">
            <img style="width: 100%; height: 80px; border-radius: 5px" :src="item.cover" alt="">
          </div>
        </div>
        <div v-if="total === 0" style="padding: 20px 0; text-align: center; font-size: 16px; color: #666">暂无数据</div>
      </el-card>
      <div>
        <el-card  style="margin-left: 5px;width: 140%">
          <div style="display: flex; align-items: center; padding-bottom: 10px; border-bottom: 1px solid #ddd">
            <div style="font-size: 20px; flex: 1">热门文章</div>
            <div style="font-size: 12px; color: #666; cursor: pointer;" @click="refreshTop"><el-icon><Refresh /></el-icon> 换一换</div>
          </div>
          <div>
            <div v-for="item in showList" :key="item.id" style="margin: 15px 0" class="line1">
              <a :href="'/detail?blogId=' + item.id" target="_blank">
      <span style="width: 18px; display: inline-block; text-align: right; margin-right: 10px">
        <span style="color: orangered" v-if="item.index === 1">{{ item.index }}</span>
        <span style="color: goldenrod" v-else-if="item.index === 2">{{ item.index }}</span>
        <span style="color: dodgerblue" v-else-if="item.index === 3">{{ item.index }}</span>
        <span style="color: #666" v-else>{{ item.index }}</span>
      </span>
              <span style="color: #666;">{{ item.title }}</span>
              </a>
            </div>
          </div>
        </el-card>

        <el-card  style="margin-left: 5px;width: 140%;margin-top: 15px">
          <div style="display: flex; align-items: center; padding-bottom: 10px; border-bottom: 1px solid #ddd">
            <div style="font-size: 20px; flex: 1">热门活动</div>
          </div>
          <div>
            <div v-for="item in showList2" :key="item.id" style="margin: 15px 0" class="line1">
      <span style="width: 18px; display: inline-block; text-align: right; margin-right: 10px">
        <span style="color: orangered" v-if="item.index === 1">{{ item.index }}</span>
        <span style="color: goldenrod" v-else-if="item.index === 2">{{ item.index }}</span>
        <span style="color: dodgerblue" v-else-if="item.index === 3">{{ item.index }}</span>
        <span style="color: #666" v-else>{{ item.index }}</span>
      </span>
              <span style="color: #666;">{{ item.name }}</span>
            </div>
          </div>
        </el-card>
      </div>


    </div>

  </div>
</template>

<script>

import request from "@/utils/api";
import head1 from "../head/index.vue"
import { Refresh,Opportunity,View,UserFilled } from '@element-plus/icons-vue'
export default {
  components:{
    head1
  },
  data() {
    return {
      q: this.$route.query.q||'',
      current: '全部博客',  //当前选中的分类名称
      categoryList: [],
      tableData: [],  // 所有的数据
      total:0,
      showList:[],
      topList:[],
      lastIndex:0,
      showList2:[]
    }
  },
  mounted() {

    this.load()
    this.loadBlogs()
    this.refreshTop()
    this.loadActivity()
  },
  methods: {
    selectCategory(categoryName) {
      this.current = categoryName
      if(categoryName=='全部博客'){
        this.loadBlogs();
        return;
      }
      request("/blog/s4/"+categoryName).then(res=>{
        this.tableData=[]
        for(let i=0;i<res.length;i++){
          this.tableData[i]=res[i]
          this.tableData[i].tags=JSON.parse(res[i].tags||'{}')
        }

        this.total=res.length
      })
    },
    load() {
      // 请求分类的数据
      request.get('/cate/all').then(res => {
        this.categoryList = res|| []
        this.categoryList.unshift({ name: '全部博客' })
      })
    },
    loadBlogs() {
      if(this.q==''){
        request.get('/blog/selectAll').then(res => {
          this.tableData=[]
          for(let i=0;i<res.length;i++){
            this.tableData[i]=res[i];
            this.tableData[i].tags=JSON.parse(res[i].tags||'{}')
          }
          this.total=res.length
        })
      }
      else{
        request.get('/blog/sq/'+this.q).then(res => {
          this.tableData=[]
          for(let i=0;i<res.length;i++){
            this.tableData[i]=res[i];
            this.tableData[i].tags=JSON.parse(res[i].tags||'{}')
          }
          this.total=res.length
        })
      }

    },
    refreshTop() {
      request.get('/blog/selectTop').then(res => {
        this.topList = res|| []
        let i = 1
        this.topList.forEach(item => item.index = i++)
        if (this.lastIndex === 20) {
          this.lastIndex = 0
        }
        this.showList = this.topList.slice(this.lastIndex, this.lastIndex+5)
        this.lastIndex += 5

      })
    },
    loadActivity(){
      request.get('/act/selectTop').then(res=>{
        this.showList2=res||[];
        let i = 1
        this.showList2.forEach(item => item.index = i++)
      })
    },
    skip(id){
      this.$router.push('/detail?blogId='+id)
    }

  }
}
</script>

<style>
.category-item {
  text-align: center;
  padding: 10px 0;
  font-size: 16px;
  cursor: pointer;
}
.category-item-active {
  background-color: #1890ff;
  color: #fff;
  border-radius: 5px;
}
.line1 {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
.blog-box {
  display: flex;
  grid-gap: 15px;
  padding: 10px 0;
  border-bottom: 1px solid #ddd;
}
.blog-box:first-child {
  padding-top: 0;
}

.front-header-left img, .front-header-dropdown img {
  width: 40px;
  height: 40px;
  border-radius: 50%
}
.front-header-left .title {
  flex: 1;
  color: #fff;
  cursor: pointer;
  margin-left: 10px;
  font-size: 20px;
  font-weight: bold;
}

.main-content {
  width: 80%;
  margin: 5px auto;
  background-color: white;
}



.el-menu-item {
  color: #fff !important;
  border: none !important;
}
.el-menu-item:not(.is-active):hover {
  background-color: #59aeff !important;
}
a {
  text-decoration: none;
  color: #000000;
}
</style>
