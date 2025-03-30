<!--主控界面-->
<script>
import { User, House, Filter, Aim,ChatDotRound,Download } from '@element-plus/icons-vue'
export default {
  data() {
    return {
      isCollapse: false,  // 不收缩
      asideWidth: '200px',
      collapseIcon: 'el-icon-s-fold',
      avatar:'',
      //user
      user:JSON.parse(localStorage.getItem("user")||'{}'),
      routerName: '系统首页',
      routerUrl:'/cl',
    }
  },
  mounted() {
    if(this.user.avatar!=null&&this.user.avatar!=undefined){
      this.avatar=this.user.avatar;
    }
  },
  methods: {
    handleCollapse() {
      this.isCollapse = !this.isCollapse
      this.asideWidth = this.isCollapse ? '64px' : '200px'
      this.collapseIcon = this.isCollapse ? 'el-icon-s-unfold' : 'el-icon-s-fold'
    }
  },
  watch: {
    $route: {
      handler: function(val, oldVal){
        this.routerUrl = val.path
        if (val.path.toString() == '/cate'){
          this.routerName = '种类管理'
        }
        else if (val.path.toString() == '/users'){
          this.routerName = '用户管理'
        }
        else if (val.path.toString() == '/blog'){
          this.routerName = '博客管理'
        }
        else if (val.path.toString() == '/center') {
          this.routerName = '个人中心'
        }
        else if(val.path.toString() =='/act'){
          this.routerName ='活动设置'
        }
        else if(val.path.toString() =='/up'){
          this.routerName ='图片上传'
        }
        else if(val.path.toString() =='/com'){
          this.routerName ='评论管理'
        }
        else if(val.path.toString() =='/join'){
          this.routerName ='活动报名'
        }
      },
      // 深度观察监听
      deep: true
    }
  },
}
</script>

<template>
  <div>
    <el-container>
      <!--    侧边栏  -->
      <el-aside :width="asideWidth" style="min-height: 100vh; background-color: #00BFF3">
        <div style="height: 60px; color: white; display: flex; align-items: center; justify-content: center">
          <span class="logo-title" v-show="!isCollapse">后台管理系统</span>
        </div>
        <el-menu
            :collapse="isCollapse"
            :collapse-transition="false" router
            style="border: none"
            :default-active="$route.path">
          <el-menu-item index="/blog">
            <el-icon><House /></el-icon>
            <span slot="title">博客首页</span>
          </el-menu-item>
          <el-menu-item index="/users" >
            <el-icon><User /></el-icon>
            <span slot="title">用户管理</span>
          </el-menu-item>
          <el-menu-item index="/cate" >
            <el-icon><Filter /></el-icon>
            <span slot="title">种类管理</span>
          </el-menu-item>
          <el-menu-item index="/act" >
            <el-icon><Aim /></el-icon>
            <span slot="title">活动设置</span>
          </el-menu-item>
          <el-menu-item index="/up" >
            <el-icon><Download /></el-icon>
            <span slot="title">图片上传</span>
          </el-menu-item>
          <el-menu-item index="/com" >
            <el-icon><ChatDotRound /></el-icon>
            <span slot="title">评论管理</span>
          </el-menu-item>
          <el-menu-item index="/join" >
            <el-icon><ChatDotRound /></el-icon>
            <span slot="title">活动报名</span>
          </el-menu-item>
        </el-menu>
      </el-aside>

      <el-container>
        <!--        头部区域-->
        <el-header>

          <i :class="collapseIcon" style="font-size: 26px" @click="handleCollapse"></i>
          <el-breadcrumb separator-class="el-icon-arrow-right" style="margin-left: 20px">
            <el-breadcrumb-item :to="{ path: '/cl' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: routerUrl }">{{ routerName }}</el-breadcrumb-item>
          </el-breadcrumb>

          <div style="flex: 1; width: 0; display: flex; align-items: center; justify-content: flex-end">


            <div class="flex flex-wrap items-center">
              <div style="display: flex; align-items: center; cursor: default">
                <div class="block">
                  <el-image
                      :src="this.user.avatar"
                      style="width: 40px;border-radius:50% ;height: 40px;display: block;margin: 0 5px; "
                      :zoom-rate="1.2"
                      :max-scale="7"
                      :min-scale="0.2"
                      fit="cover"
                      :preview-src-list="[this.user.avatar]"
                      :initial-index="4"
                      preview-teleported="true"
                      :viewer-options="{borderRadius:'50%' }"
                  />
                </div>
                <el-dropdown>
                  <el-button >
                    <img src="@/assets/chilun.png" alt="" style="width: 18px; height: 18px;margin-right: 2px"><el-icon class="el-icon--right"><arrow-down /></el-icon>
                  </el-button>
                  <template #dropdown>
                    <el-dropdown-menu>
                      <el-dropdown-item @click="this.$router.push('/center')">个人中心</el-dropdown-item>
                      <el-dropdown-item @click="this.$router.push('/log')">退出登录</el-dropdown-item>
                      <el-dropdown-item @click="this.$router.push('/')">首页</el-dropdown-item>
                    </el-dropdown-menu>
                  </template>
                </el-dropdown>
              </div>
            </div>
          </div>

        </el-header>

        <!--        主体区域-->
        <router-view></router-view>

      </el-container>


    </el-container>
  </div>
</template>

<style scoped>
.el-menu {
  font-family: Arial, 华文中宋;
  --el-menu-horizontal-height: 80px;
  --el-menu-horizontal-sub-item-height: 36px;
  --el-menu-item-font-size: 20px;
  background-color: #00BFF3;
  --el-menu-text-color: #ffffff;
  --el-menu-hover-text-color: #00BFF3;
  --el-menu-bg-color: #00BFF3;
  --el-menu-hover-bg-color: #fff;
  --el-menu-item-height: 70px
}
.el-menu-item.is-active {
  color: #00BFF3 !important;
  background-color: #fff;
}
.el-menu-item:hover {
  background-color: var(--el-menu-hover-bg-color);
  color: #00BFF3;
}

.el-aside {
  transition: width .3s;
  box-shadow: 2px 0 6px rgba(0,21,41,.35);
}
.logo-title {
  font-size: 28px;
  transition: all .3s;   /* 0.3s */
  font-family: Arial, 黑体;
}
.el-header {
  box-shadow: 2px 0 6px rgba(0,191,243,.1);
  display: flex;
  align-items: center;
  background-color: rgba(0,191,243,0.2);
  width: 100%;
  min-width: 1000px;
  padding: 0px;
}
</style>
