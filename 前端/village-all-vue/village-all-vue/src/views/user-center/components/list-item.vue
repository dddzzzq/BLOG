<script setup>
import { computed, defineProps } from "vue";

const props = defineProps({ item: Object })
</script>

<template>
  <div class="item">
    <div class="item-img">
      <el-image
          style="width: 320px; height: 180px; border-radius: 10px"
          :src="props.item.cover"
          :zoom-rate="1.2"
          :max-scale="7"
          :min-scale="0.2"
          :preview-src-list="[props.item.cover]"
          :initial-index="4"
          fit="cover"
      />
    </div>
    <div class="item-main">
      <div class="item-title">
        {{ props.item.title }}
      </div>
      <div class="item-descr">
        {{ props.item.descr }}
      </div>
      <div class="item-tags">
        <el-tag
            type="primary"
            v-for="tag in  props.item.tags"

            style="margin-right: 5px; font-size: 16px"
        >
          {{ tag }}
        </el-tag>
      </div>
      <div class="item-other">
        <div class="item-date">
          发布时间：{{ props.item.date }}
        </div>
        <div class="item-achieve">
          <div class="item-star">
            <img src="/src/assets/star.png" alt="">
            <span>{{ props.item.star }}</span>
          </div>
          <div class="item-likeCount">
            <img src="/src/assets/likeCount.png" alt="">
            <span>{{ props.item.likeCount }}</span>
          </div>
          <div class="item-read">
            <img src="/src/assets/read.png" alt="">
            <span>{{ props.item.read }}</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.item {
  display: flex;
  width: 100%;
}
.item-main {
  margin-left: 30px;
  width: calc(100% - 340px);
}
.item-title {
  margin-top: 5px;
  font-size: 30px;
  line-height: 1;
  //color: #097bfe;
}
.item-descr {
  width: 820px;
  margin-top: 15px;
  white-space: pre-wrap;
  line-height: 1.5;
  color: #505050;
  height: 45px;
}
.item-tags {
  margin-top: 10px;
}
.el-tag {
  align-items: center;
  background-color: var(--el-tag-bg-color);
  border-color: var(--el-tag-border-color);
  border-radius: var(--el-tag-border-radius);
  border-style: solid;
  border-width: 1px;
  box-sizing: border-box;
  color: #097bfe;
  display: inline-flex;
  font-size: 18px;
  height: 30px;
  justify-content: center;
  line-height: 1;
  padding: 0 9px;
  vertical-align: middle;
  white-space: nowrap;
}
.item-other {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 18px;
  margin-top: 20px;
}
.item-achieve {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 220px;
  margin-right: 20px;
}
.item-star,
.item-likeCount,
.item-read {
  display: flex;
  align-items: center;
  img {
    height: 20px;
  }
  span {
    margin-left: 5px;
  }
}
</style>
