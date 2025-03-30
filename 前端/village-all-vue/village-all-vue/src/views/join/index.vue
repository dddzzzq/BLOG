<template>
  <div>

    <div class="table">
      <el-table :data="tableData" stripe  border style="margin-left: 10px;margin-top: 20px;margin-right: 20px">
        <el-table-column prop="id" label="序号" width="80" align="center" sortable></el-table-column>
        <el-table-column prop="activityId" label="活动ID" show-overflow-tooltip></el-table-column>
        <el-table-column prop="activityName" label="活动名称" show-overflow-tooltip></el-table-column>
        <el-table-column prop="userId" label="报名人ID" show-overflow-tooltip></el-table-column>
        <el-table-column prop="userName" label="报名人" show-overflow-tooltip></el-table-column>
        <el-table-column prop="time" label="报名时间"></el-table-column>

        <el-table-column label="操作" width="180" align="center">
          <template v-slot="scope">
            <el-button plain type="danger" size="mini" @click=del(scope.row.id)>删除</el-button>
          </template>
        </el-table-column>
      </el-table>


    </div>


  </div>
</template>

<script>
import request from "@/utils/api";

export default {
  name: "ActivitySign",
  data() {
    return {
      tableData: [],  // 所有的数据
      total: 0,
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {
      },
      ids: []
    }
  },
  created() {
    this.load()
  },
  methods: {
    del(id) {
      this.$confirm('您确定删除吗？', '确认删除', {type: "warning"}).then(response => {
        request.get('/sign/del/' + id).then(res => {
            this.$message.success('操作成功')
            this.load()
        })
      }).catch(() => {
      })
    },
    load() {
      request.get('/sign/all').then(res => {
        this.tableData = res
        this.total = res.length
      })
    },

  }
}
</script>

<style scoped>

</style>
