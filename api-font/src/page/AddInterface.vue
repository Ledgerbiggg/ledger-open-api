<template>
  <div class="step">
    <el-steps :space="200" :active="store.state.active" simple>
      <el-step title="接口信息" :icon="Edit"/>
      <el-step title="接口请求参数信息" :icon="UploadFilled"/>
      <el-step title="接口相应参数信息" :icon="Picture"/>
    </el-steps>
  </div>
  <div v-if="store.state.active===1">
    <InterfaceDetail/>
  </div>
  <div v-if="store.state.active===2">
    <RequestParamsInfo/>
  </div>
  <div v-if="store.state.active===3">
    <ResponseParamsInfo/>
  </div>
</template>

<script setup>
import {Edit, Picture, UploadFilled} from '@element-plus/icons-vue'
import InterfaceDetail from "@/page/AddInterface/InterfaceDetail.vue";
import RequestParamsInfo from "@/page/AddInterface/RequestParamsInfo.vue";
import ResponseParamsInfo from "@/page/AddInterface/ResponseParamsInfo.vue";
import store from "@/store/store";


</script>

<style scoped>
.step{
  width: 90%;
  margin: 2vh auto;
}
</style>