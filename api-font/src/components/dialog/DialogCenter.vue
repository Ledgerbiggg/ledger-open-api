<template>
  <el-dialog
      v-model="centerDialogVisible"
      title="温馨提示"
      width="30%"
      align-center
  >
    <span>
      <slot name="content"></slot>
    </span>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="centerDialogVisible = false">
          <slot name="btn"></slot>
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>
<script setup>
import {ref} from 'vue'

const centerDialogVisible = ref(true)


</script>

<style scoped>

</style>