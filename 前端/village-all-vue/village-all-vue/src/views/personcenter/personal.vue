<!--个人中心-->
<template>
  <el-container>
    <!--        头部区域-->
    <el-header>
      <el-breadcrumb separator-class="el-icon-arrow-right" style="margin-left: 20px">
        <el-breadcrumb-item :to="{ path: '/cl' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/center' }">个人中心</el-breadcrumb-item>
      </el-breadcrumb>

      <div style="flex: 1; width: 0; display: flex; align-items: center; justify-content: flex-end">
        <div class="flex flex-wrap items-center">
          <div style="display: flex; align-items: center; cursor: default">
<!--            <el-image
                :src="this.user.avatar"
                style="width: 40px;border-radius:50% ;height: 40px;display: block;margin: 0 5px; "
                :zoom-rate="1.2"
                :max-scale="7"
                :min-scale="0.2"
                fit="cover"
                :preview-src-list="[this.user.avatar]"
                :initial-index="4"
                preview-teleported="true"
            />-->
            <el-dropdown>
              <el-button >
                设置<el-icon class="el-icon--right"><arrow-down /></el-icon>
              </el-button>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item @click="this.$router.push('/cl')">返回首页</el-dropdown-item>
                </el-dropdown-menu>
                <el-dropdown-menu>
                  <el-dropdown-item @click="deleteOneselfVisible = true">注销账号</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
        </div>
      </div>

    </el-header>
  </el-container>

  <div style="margin-left: 400px;margin-top: 10px">
    <el-card style="width: 60%">
      <div style="display: flex">
        <img src="@/assets/center.png"  style="width: 22px; height: 22px;">
        <div style="margin-left: 2px;">个人中心</div>
      </div>
      <el-form :model="user" label-width="80px" style="padding-right: 20px">
        <div style="margin: 15px; text-align: center">
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
        </div>
       <el-form-item label="用户名" prop="username">
          <el-input v-model="user.username" placeholder="用户名" disabled></el-input>
        </el-form-item>
        <!-- <el-form-item label="角色" prop="authority">
          <el-input v-model="user.authority" placeholder="角色" disabled></el-input>
        </el-form-item>-->
<!--        <el-form-item label="创建日期" prop="time">
          <el-input v-model="user.time" placeholder="注册日期" disabled></el-input>
        </el-form-item>-->
        <el-form-item label="姓名" prop="name">
          <el-input v-model="user.name" placeholder="姓名"></el-input>
        </el-form-item>
        <el-form-item label="籍贯" prop="name">
          <el-input v-model="user.place" placeholder="籍贯"></el-input>
        </el-form-item>
        <el-form-item label="学校" prop="name">
          <el-input v-model="user.school" placeholder="学校"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="user.phone" placeholder="电话"></el-input>
        </el-form-item>
        <el-form-item label="性别" >
          <el-select v-model="user.sex" placeholder="请选择性别" style="margin-left: 10px">
            <el-option
                label="男"
                value="男">
            </el-option>
            <el-option
                label="女"
                value="nv">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="个性签名" prop="info">
          <el-input type="textarea" v-model="user.info" placeholder="个性签名"></el-input>
        </el-form-item>

        <div style="text-align: center; margin-bottom: 20px">
          <el-button type="primary" @click="update">保 存</el-button>
        </div>


      </el-form>
    </el-card>
  </div>
<!--  ====== 注销弹框========== -->
  <el-dialog
      v-model="deleteOneselfVisible"
      title="注销当前用户"
      width="500"
  >
    <span>你确定要注销当前用户: {{ user.username }}</span>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="deleteOneselfVisible = false">取消</el-button>
        <el-button type="primary" @click="deleteOneself">
          确定
        </el-button>
      </div>
    </template>
  </el-dialog>


</template>

<script>
import request from "@/utils/api";
var token=JSON.parse(localStorage.getItem("user") || '{}').token
export default {
  name: "personal",
  data() {
    return {
      user: JSON.parse(localStorage.getItem("user") || '{}'),
      deleteOneselfVisible: false,
      myHeaders: {token: token}
    }
  },
  created() {
  },
  methods: {
    //初始化：将登录人信息加载出来
    update() {
        request.post('/update',this.user).then(res=>{
          if(res.code==='200'){
            this.$message.success("保存成功！")
            localStorage.setItem("user",JSON.stringify(this.user));
          }
        })

    },
    handleAvatarSuccess(response, file, fileList) {
      this.user.avatar = response.data
    },
    deleteOneself () {
      request.get("/del/" + this.user.id).then(res => {
        if (res.code == 200 ) {
          this.$message.success("删除成功")
          localStorage.clear()
          this.$router.replace({path:'/'})
        }
        else {
          this.$message.error('删除失败')
        }
      })
    }
  }
}
</script>

<style scoped>
:deep(.el-header ) {
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: rgba(0,191,243,0.2);
}
:deep(.el-form-item__label) {
  font-weight: bold;
}
:deep(.el-upload)
 {
  border-radius: 50%;
}
:deep(.avatar-uploader .el-upload) {
  border: 1px dashed #d9d9d9;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  border-radius: 50%;
}
:deep(.avatar-uploader .el-upload:hover) {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
  border-radius: 50%;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
  border-radius: 50%;
}
</style>
