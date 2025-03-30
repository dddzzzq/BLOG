<!--分类页-->
<template>
  <div>

    <div style="display: flex;margin-top: 20px">
      <el-input v-model="name" placeholder="请输入要查询的名称" style="width: 300px;margin-left: 20px;"></el-input>
      <el-button type="success" style="margin-left: 5px" @click="s1()"><img src="@/assets/search.png" alt="" style="width: 18px; height: 18px;margin-right: 2px"></el-button>
      <el-button type="info" style="margin-left: 5px" @click="reset"><img src="@/assets/return.png" alt="" style="width: 18px; height: 18px;margin-right: 2px"></el-button>
    </div>
    <div style="width: 18px;height: 18px;margin-left: 20px;margin-top: 10px">
      <el-tooltip  effect="dark" content="分类新增" placement="bottom" style="width: 30px">
        <el-button type="primary" @click="handleAdd()"><img src="@/assets/add.png" alt="" style="width: 18px; height: 18px;margin-right: 2px"></el-button>
      </el-tooltip>
    </div>


    <div class="table"  style="margin-left: 20px;border-radius: 5px;margin-top: 20px;width: 97%">
      <el-table :data="tableData.slice((currentPage - 1)*pageSize, currentPage*pageSize)" border stripe>
        <el-table-column prop="id" label="序号" width="70" align="center" sortable></el-table-column>
        <el-table-column prop="name" label="分类名称"></el-table-column>
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


    <el-dialog title="信息" v-model="fromVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
      <el-form :model="form" label-width="100px" style="padding-right: 50px" :rules="rules" ref="formRef">
        <el-form-item label="分类名称" prop="name">
          <el-input v-model="form.name" placeholder="分类名称"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>


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
      fromVisible: false,
      form: {},
      user: JSON.parse(localStorage.getItem('user') || '{}'),
      rules: {
        name: [
          {required: true, message: '请输入名称', trigger: 'blur'},
        ]
      },
      ids: [],
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
    handleAdd() {
      this.form = {}
      this.fromVisible = true
    },

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
        request.get('/cate/del/' + id).then(res => {
          this.$message.success("删除成功")
          this.load();
        })
      }).catch(() => {
      })
    },
    s1(){
      if(this.name==''||this.name==null) return;
      request.get("/cate/s1/"+this.name).then(res=>{
        this.tableData=res;
      })
    },
    load() {
      request.get('/cate/all').then(res => {
        this.tableData = res
      })
    },
    reset() {
      this.name = null
      this.load()
    },
    PageChange (val) {
      this.currentPage = val;
    }
  }
}
</script>

<style scoped>

</style>
