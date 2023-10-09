<template>
  <div>
    <el-dialog v-model="store.state.showResultDialog" title="查看详情" width="30%" draggable>
      <span>
          <v-md-preview :text="'```json\n'+store.state.callDetail.result" @copy-code-success="handleCopyCodeSuccess"></v-md-preview>
      </span>
      <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="store.state.showResultDialog = false">确认</el-button>
      </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import store from "@/store/store";
import {ElMessage} from "element-plus";
const handleCopyCodeSuccess=()=>{
  ElMessage.success("复制成功")
}

</script>


<style scoped>

</style>