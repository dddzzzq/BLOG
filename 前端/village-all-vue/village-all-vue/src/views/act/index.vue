<!--前台活动界面-->
<template>
  <div class="main-content">
    <el-row :gutter="10">
      <el-col :span="6" v-for="item in tableData" :key="item.id">
        <el-card class="card" style="margin-bottom: 10px">
          <img :src="item.cover" alt="" style="width: 100%; height: 150px; border-radius: 5px">
          <div style="margin: 10px 0; font-weight: bold">{{ item.name }}</div>
          <div style="display: flex; align-items: center">
            <div style="flex: 1; color: #666"> <el-icon><Calendar /></el-icon> {{ item.start }}</div>
            <el-button type="primary" disabled v-if="item.isEnd">已结束</el-button>
            <el-button type="primary" v-else @click="goDetail(item.id)">详情</el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>

  </div>
</template>

<script>
import request from "@/utils/api";
import { Calendar} from "@element-plus/icons-vue"
export default {
  name: "Activity",
  data() {
    return {
      tableData: [],  // 所有的数据
      name: null,
    }
  },
  created() {
    this.load(1)
  },
  methods: {
    goDetail(id) {
      this.$router.push('/activityDetail?activityId=' + id)
    },
    load() {
      request.get('/act/all', ).then(res => {
        this.tableData = res
        this.total = res.length
      })
    },

  }
}
</script>

<style scoped>

</style>