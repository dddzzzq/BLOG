<template>
  <div class="login" style="  height: 100vh;display: flex;align-items: center;justify-content:center; ">
    <div style="display: flex; background-color: white;border-radius: 5px;overflow: hidden;">
      <div style="flex: 1">
        <img src="@/assets/log1.png " alt="" style="width: 120%;height: 450px">
      </div>
      <div style="flex: 1">
        <el-form :model="user" style="width: 80%;margin-left: 42px">
          <div style="font-size: 25px;font-width: bold;text-align: center;margin-bottom: 40px;margin-top: 30px" >欢迎登录博客</div>
          <el-form-item prop="username">
            <el-input suffix-icon="el-icon-User"  style="width: 100%" placeholder="请输入账号" v-model="user.username"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input type="password" style="width: 100%" placeholder="请输入密码" v-model="user.password"></el-input>
          </el-form-item>
          <el-form-item prop="code">
            <div style="display: flex">
              <el-input  size="large" style="flex: 1;width: 95%;margin-top: 5px" placeholder="请输入验证码" v-model="user.code"></el-input>
              <div style="flex: 1;height: 36px;width: 30px"  >

                <valid-code @update:value="getCode"></valid-code>
              </div>
            </div>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" style="flex: 1" @click="test">登录</el-button>
          </el-form-item>

        </el-form>

      </div>
    </div>
  </div>
</template>

<script >

import validCode from "@/components/ValidCode.vue";
import request from "@/utils/api";
export default {
  name: "login",
  components: {
    validCode
  },
  data()
  {
    return{
      code:null,
      user:{
        username: null,
        password:null,
        code:null
      },
    }
  },
  methods:{
    getCode(code){
      this.code=code;
    },
    //将后端数据传回前端，进行判断
    test()
    {

        if(this.user.username==null||this.user.password==null||this.user.code==null){
          this.$message.error("请将信息填写完整！")
          return
        }

        if(this.code.toLowerCase()!=this.user.code.toLowerCase()){
          this.$message.error("验证码错误，请重试！")
          return
        }
        request.post("/judge",this.user).then(res=>{
            if(res.data==null) {
              this.$message.error("用户信息错误，请重试！")
              return;
            }
            if(res.code==200){
              if(res.data.authority!='最高管理员'){
                this.$message.error("权限不足，请联系管理员")
                return
              }
              this.$message.success("你好"+res.data.username+"!");
              localStorage.setItem("user",JSON.stringify(res.data));
             this.$router.push({path:'/cl'})
            }

        })

    }

  }
}
</script>


<style scoped>
.login{
  background-color: palegreen;
}
</style>