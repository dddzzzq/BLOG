<template>
  <el-table :data="tableData.slice((currentPage - 1)*pageSize, currentPage*pageSize)" style="margin-left: 20px;margin-top: 20px" border stripe >
    <el-table-column prop="id" label="序号" width="70" align="center" sortable></el-table-column>
    <el-table-column prop="content" label="内容" show-overflow-tooltip></el-table-column>
    <el-table-column prop="uid" label="评论人"></el-table-column>
    <el-table-column prop="pid" label="父级ID"></el-table-column>
    <el-table-column prop="rid" label="根节点ID"></el-table-column>
    <el-table-column prop="time" label="评论时间"></el-table-column>
    <el-table-column prop="fid" label="关联ID"></el-table-column>
    <el-table-column prop="module" label="模块"></el-table-column>
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
<!--  <el-form :model="form" label-width="100px" style="padding-right: 50px" :rules="rules" ref="formRef">
    <el-form-item label="内容" prop="content">
      <el-input v-model="form.content" placeholder="内容"></el-input>
    </el-form-item>
    <el-form-item label="评论人" prop="userId">
      <el-input v-model="form.uid" placeholder="评论人"></el-input>
    </el-form-item>
    <el-form-item label="父级ID" prop="pid">
      <el-input v-model="form.pid" placeholder="父级ID"></el-input>
    </el-form-item>
    <el-form-item label="根节点ID" prop="rootId">
      <el-input v-model="form.rid" placeholder="根节点ID"></el-input>
    </el-form-item>
    <el-form-item label="评论时间" prop="time">
      <el-input v-model="form.time" placeholder="评论时间"></el-input>
    </el-form-item>
    <el-form-item label="关联ID" prop="fid">
      <el-input v-model="form.fid" placeholder="关联ID"></el-input>
    </el-form-item>
    <el-form-item label="模块" prop="module">
      <el-input v-model="form.module" placeholder="模块"></el-input>
    </el-form-item>
  </el-form>-->
</template>

<script>
import request from "@/utils/api";

export default {
  name: "index",
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
      pageSize: 8,
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
    del(id) {   // 单个删除
      this.$confirm('您确定删除吗？', '确认删除', {type: "warning"}).then(response => {
        request.get('/com/del/' + id).then(res => {
          this.$message.success("删除成功")
          this.load();
        })
      }).catch(() => {
      })
    },
    load() {
      request.get('/com/all').then(res => {
        console.log(res)
        this.tableData = res
      })
    },
    PageChange(val) {
      this.currentPage = val
    }

  }
}
</script>

<style scoped>

</style>