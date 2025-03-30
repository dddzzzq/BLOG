<template>
  <div style="display: flex;margin-top: 20px">
    <el-input v-model="username" placeholder="请输入要查询的用户名" style="width: 300px;margin-left: 20px;"></el-input>
    <el-button type="success" style="margin-left: 5px" @click="searchUsername()"><img src="@/assets/search.png" alt="" style="width: 18px; height: 18px;margin-right: 2px"></el-button>
    <el-button type="info" style="margin-left: 5px" @click="back"><img src="@/assets/return.png" alt="" style="width: 18px; height: 18px;margin-right: 2px"></el-button>
  </div>

  <div style="width: 18px;height: 18px;margin-left: 20px;margin-top: 10px">
    <el-tooltip  effect="dark" content="用户新增" placement="bottom" style="width: 30px">
      <el-button type="primary" @click="open_add()"><img src="@/assets/add.png" alt="" style="width: 18px; height: 18px;margin-right: 2px"></el-button>
    </el-tooltip>
  </div>
  <!--====================================新增弹窗========================================-->
  <el-dialog v-model="dialogFormVisible" title="新增用户" @close="closeDialog()" :close-on-click-modal="false" >
    <el-form :model="form" :inline="true">
      <div>
        <el-form-item label="用户名"  style="width: 100% ;margin-right: 30px">
          <el-input style="margin-right: 60px"  v-model="form.username" autocomplete="off" placeholder="请输入用户名"></el-input>
        </el-form-item>

      </div>
      <el-form-item label="密码"  style="width: 100%;margin-right: 30px">
        <el-input v-model="form.password" autocomplete="off" placeholder="请输入密码" style="margin-right: 60px;margin-left: 10px"></el-input>
      </el-form-item>
      <div>
        <el-form-item label="类型" label-width:100px style="flex: 1">
          <el-select v-model="form.authority" placeholder="请选择类型" style="margin-left: 10px">
            <el-option v-if="this.user.username=='Iscream'" value="最高管理员" ></el-option>
            <el-option
                label="普通用户"
                value="普通用户">
            </el-option>
            <el-option
                label="发布人员"
                value="发布人员">
            </el-option>

          </el-select>
        </el-form-item>
      </div>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="cancel()">取消</el-button>
        <el-button type="primary"  @click="confirm()">
          确定
        </el-button>
      </span>
    </template>
  </el-dialog>
  <!--  ===========修改弹窗==============  -->
  <el-dialog
      v-model="deleteVisible"
      title="删除用户"
      width="500"
  >
    <span>你确定要删除该用户？</span>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="deleteCancel()">取消</el-button>
        <el-button type="primary" @click="deleteUser()">
          确定
        </el-button>
      </div>
    </template>
  </el-dialog>
  <el-dialog
      v-model="v5"
      title="修改权限"
      width="500"
      :close-on-click-modal="false" destroy-on-close
  >
    <el-form :model="form4" label-width="100px" style="padding-right: 50px"  ref="formRef">
        <el-select v-model="form4.authority" style="width: 100%" >
          <el-option value="发布人员" ></el-option>
          <el-option value="普通用户" ></el-option>
          <el-option v-if="this.user.username=='Iscream'" value="最高管理员" ></el-option>
        </el-select>
    </el-form>

    <template #footer>
      <div class="dialog-footer">
        <el-button type="primary" @click="fix()">
          确定
        </el-button>
      </div>
    </template>
  </el-dialog>

  <div style="margin-left: 20px;border-radius: 5px;margin-top: 20px;width: 100%">
    <el-table
        border
        height="500px"
        :default-sort = "{prop: 'id', order: 'ascending'}"
        :data="tableData.slice((currentPage - 1) * pageSize, currentPage * pageSize)"
        stripe
        style="width: 100%"
        :row-class-name="isDongkou"
    >
      <el-table-column
          width="40%"
          prop="id"
          label="编号"
          fixed
          >
      </el-table-column>
      <el-table-column
          prop="username"
          label="用户名"
          width="100%"
          fixed
          show-overflow-tooltip
      >
      </el-table-column>
      <el-table-column
          prop="password"
          width="90%"
          label="密码"
          show-overflow-tooltip
      >
      </el-table-column>
      <el-table-column
          prop="name"
          label="姓名"
          width="70%">
      </el-table-column>
      <el-table-column
          width="100%"
          label="电话号"
          prop="phone"
          show-overflow-tooltip>
      </el-table-column>
      <el-table-column
          width="70%"
          label="籍贯"
          prop="place"
          show-overflow-tooltip>
      </el-table-column>
      <el-table-column
          width="100%"
          label="学校"
          prop="school"
          show-overflow-tooltip>
      </el-table-column>
      <el-table-column
          prop="info"
          label="个性留言"
          width="90%"
          show-overflow-tooltip
      >
      </el-table-column>
      <el-table-column
          label="性别"
          prop="sex"
          width="50%"
          show-overflow-tooltip
      >
      </el-table-column>
      <el-table-column
          label="头像"
          width="90%"
          prop="avatar">
        <template #default="scope">
          <el-image
              style="width: 50px; height: 50px; border-radius: 50%"
              :src="scope.row.avatar"
              :zoom-rate="1.2"
              :max-scale="7"
              :min-scale="0.2"
              fit="cover"
              :preview-src-list="[scope.row.avatar]"
              :initial-index="4"
              preview-teleported="true"
          />
        </template>
      </el-table-column>
      <el-table-column
          label="注册时间"
          prop="time"
          width="110%">
      </el-table-column>
      <el-table-column
          label="权限"
          width="100%"
          prop="authority">
      </el-table-column>
      <el-table-column
          label="操作"
      width="130%">
        <template v-slot="scope">
          <div style="display: flex">
            <el-button type="danger" style="flex: 1;" size="small" @click="deleteOpen(scope.row)">删除</el-button>
            <el-button type="danger"  style="flex: 1;" size="small" @click="this.fixOpen(scope.row)">修改</el-button>
          </div>
        </template>
      </el-table-column>

    </el-table>
    <!--  分页-->
    <div class="pagination" style="display: flex; justify-content: right; margin-top: 20px">
      <el-pagination
          background
          layout="prev, pager, next"
          :page-size="pageSize"
          :total="tableData.length"
          @current-change="PageChange">
      </el-pagination>
    </div>
  </div>
  <!--  =========================table========================================-->
</template>

<script>
import {Stamp} from "@element-plus/icons-vue";
import request from "@/utils/api";

export default {
  components: {Stamp},
  data() {

    return {
      //user值
      user: JSON.parse(localStorage.getItem("user") || '{}'),
      //删除id传值
      form3: {
        id: ''
      },
      //删除弹窗
      dialogDelete: false,
      //新增弹窗
      dialogFormVisible: false,
      // 删除用户
      deleteVisible: false,
      v5:false,
      form1:{
        password:'',
      },
      form4:{},
      // 修改弹窗开关
      changeFormVisible: false,
      //username
      username: '',
      // 姓名
      name: '',
      form: {
        username: '',
        password: '',
        authority: '',
        time:'',
      },
      //表格数据
      tableData: [],
      //记录条数，分页时使用
      length: 0,
      pageSize: 5,
      currentPage: 1,
    }
  },
  /**
   * @brief 钩子函数
   * @author Iscream
   */
  created() {
    if(this.user.authority==undefined||this.user.authority!='最高管理员'){
      this.$router.push("/")
      this.$message.error("权限不足！")
    }
    this.getAll();

  },

  methods: {
    fix(){
      request.post('/update',this.form4).then(res=>{
        this.$message.success("修改成功！")
        this.v5=false;
        this.getAll();
      })
    },
    //获取当前日期并以字符串的形式返回
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
    //搜索返回
    back() {
      this.getAll();
    },
    //将数据加载出来
    getAll() {
      request.get('/all').then(res => {
        //将length赋值，为分页做预处理
        this.length = res.data.length;
        this.tableData = res.data;
      })
    },
    //分页查询函数
    Page(val) {
      this.currentPage = val
    },
    //根据用户名进行查询
    searchUsername() {
      if (this.username != '') {
        request.get('/getUsername/' + this.username).then(res => {
          this.tableData = [];
          if (res != null)
            this.length = res.length
          for (var i = 0; i < res.length; i++) {
            this.tableData[i] = res[i];
          }
          console.log(this.tableData)
        })
      }
    },
    //新增弹窗打开
    open_add() {
      this.dialogFormVisible = true;
      this.form = {};
    },
    cancel() {
      this.form = {}
      this.dialogFormVisible = false;
    },
    deleteOpen(item) {
      this.deleteVisible = true
      this.form = item
    },
    fixOpen(item){
      this.form4=item;
      this.v5=true;


    },
    deleteCancel() {
      this.deleteVisible = false
      this.form = {}
    },
    closeDialog() {
      this.dialogFormVisible = false;
    },
    confirm() {
      if(this.form.authority==''||this.form.password==''||this.form.username==''||this.form.authority==undefined||this.form.password==undefined||this.form.username==undefined)
      {
        this.$message.info("请将信息填写完整")
        return;
      }

      this.form.time=this.getCurrentDate();

      request.post("/add",this.form).then(res=>{
        if(res.code==200){
          this.$message.success("创建成功！")
          this.getAll();
          this.dialogFormVisible=false;
        }
        else
          this.$message.error("用户名重复，请重试！")

      })
    },
    deleteUser() {
        request.get("/del/" + this.form.id).then(res => {
          if (res.code == 200 ) {
            console.log(res)
            this.$message.success("删除成功")
            this.getAll();
            this.deleteVisible = false
          }
          else {
            this.$message.error('删除失败')
          }
        })

    },
    isDongkou({row, rowIndex}) {
      if(row.username == "dongkou123") {
        console.log(row.username)
        return 'hidden-row'
      }
      return ''
    },
    PageChange (val) {
      this.currentPage = val
    }
  }
}
</script>

<style>
.el-table .hidden-row {
  display: none !important;
}
</style>
