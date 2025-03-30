<!--上传页-->
<template>

  <div style="display: flex;margin-top: 10px">
    <el-input v-model="name" placeholder="请输入要查询的名称" style="width: 300px;margin-left: 20px;"></el-input>
    <el-button type="success" style="margin-left: 5px" @click="s1()"><img src="@/assets/search.png" alt="" style="width: 18px; height: 18px;margin-right: 2px"></el-button>
    <el-button type="info" style="margin-left: 5px" @click="reset"><img src="@/assets/return.png" alt="" style="width: 18px; height: 18px;margin-right: 2px"></el-button>
  </div>
      <el-upload
          :action="'http://localhost:80/file/upload'"
          :headers="{ token: user.token }"
          list-type="picture"
          :on-success="handleCoverSuccess"
          style="margin-top: 3px"
      >

        <div style="width: 18px;height: 18px;margin-left: 20px;margin-top: 10px">
          <el-tooltip  effect="dark" content="图片上传" placement="bottom" style="width: 30px">
            <el-button type="primary" ><img src="@/assets/add.png" alt="" style="width: 18px; height: 18px;margin-right: 2px"></el-button>
          </el-tooltip>
        </div>
      </el-upload>

  <div>
    <div class="table"  style="margin-left: 20px;border-radius: 5px;margin-top: 20px;width: 97%">
      <el-table :data="tableData.slice((currentPage - 1)*pageSize, currentPage*pageSize)"  border stripe>
        <el-table-column prop="id" label="序号" width="70" align="center" sortable></el-table-column>
        <el-table-column prop="src" label="路径" width="500%" align="center" ></el-table-column>
        <el-table-column prop="src" label="内容">
          <template v-slot="scope">
            <div style="display: flex; align-items: center">
              <el-image
                  style="width: 50px; height: 50px; border-radius: 5px"
                  v-if="scope.row.src"
                  :src="scope.row.src"
                  :initial-index="4"
                  :preview-src-list="[scope.row.src]"></el-image>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="username" label="上传人"></el-table-column>
        <el-table-column label="操作" align="center" width="180">
          <template v-slot="scope">
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

  </div>
</template>

<script>
import request from "@/utils/api";

export default {
  name: "Category",
  data() {
    return {
      tableData: [],  // 所有的数据
      name: null,
      form: {},
      user: JSON.parse(localStorage.getItem('user') || '{}'),
      rules: {
        name: [
          {required: true, message: '请输入名称', trigger: 'blur'},
        ]
      },
      pageSize: 5,
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
    save() {   // 保存按钮触发的逻辑  它会触发新增或者更新
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          request({
            url:'/cate/ins',
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
    del(id) {   // 单个删除
      this.$confirm('您确定删除吗？', '确认删除', {type: "warning"}).then(response => {
        request.get('/upload/del/' + id).then(res => {
          this.$message.success("删除成功")
          this.load();
        })
      }).catch(() => {
      })
    },
    s1(){
      if(this.name==''||this.name==null) return;
      request.get("/upload/s1/"+this.name).then(res=>{
        this.tableData=res;
      })
    },
    load() {
      request.get('/upload/all').then(res => {
        this.tableData = res
      })
    },
    reset() {
      this.name = null
      this.load()
    },
    handleCoverSuccess(res){
      this.form.src = res.data
      this.form.username=this.user.username;
      request.post("/upload/ins",this.form).then(res=>{
        this.$message.success("上传成功！")
        this.load();
      })
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
