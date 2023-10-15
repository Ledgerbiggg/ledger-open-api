<template>
    <el-dialog v-model="store.state.requestParametersEditDialog" title="修改个人信息">
      <el-form :model="form">
        <el-form-item label="昵称:" :label-width="formLabelWidth">
          <el-input v-model="form.name" autocomplete="off" />
        </el-form-item>
        <el-form-item label="Zones" :label-width="formLabelWidth">
          <el-select v-model="form.region" placeholder="Please select a zone">
            <el-option label="Zone No.1" value="shanghai" />
            <el-option label="Zone No.2" value="beijing" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="store.commit('showUserInfoDialog',false)">Cancel</el-button>
        <el-button type="primary" @click="store.commit('showUserInfoDialog',false)">
          Confirm
        </el-button>
      </span>
      </template>
    </el-dialog>
</template>

<script setup>
import { reactive} from 'vue'
import store from "@/store/store";



const formLabelWidth = '140px'

const form = reactive({
  name: '',
  region: '',
  date1: '',
  date2: '',
  delivery: false,
  type: [],
  resource: '',
  desc: '',
})

</script>
<style scoped>
.el-button--text {
  margin-right: 15px;
}
.el-select {
  width: 300px;
}
.el-input {
  width: 300px;
}
.dialog-footer button:first-child {
  margin-right: 10px;
}
</style>