<template>
  <div class="req">
    <el-dialog v-model="store.state.responseParametersEditDialog" title="修改响应参数">
      <el-form :model="responseParameterDetails">
        <el-form-item label="名称:" :label-width="formLabelWidth">
          <el-input v-model="responseParameterDetails.name" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="类型" :label-width="formLabelWidth">
          <el-select v-model="responseParameterDetails.type" placeholder="请选择">
            <el-option label="string" value="string"/>
            <el-option label="int" value="int"/>
            <el-option label="double" value="double"/>
            <el-option label="boolean" value="boolean"/>
            <el-option label="json" value="json"/>
            <el-option label="array" value="array"/>
          </el-select>
        </el-form-item>
        <el-form-item label="描述:" :label-width="formLabelWidth">
          <el-input v-model="responseParameterDetails.description" autocomplete="off"/>
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="store.commit('showResponseParametersEditDialog',false)">取消</el-button>
        <el-button type="primary" @click="sumbit()">
          确认
        </el-button>
      </span>
      </template>
    </el-dialog>

  </div>
</template>


<script setup>
import {computed, defineProps} from 'vue'
import store from "@/store/store";
import http from "@/js/http";
import {ElMessage} from "element-plus";
// import http from "@/js/http";

const responseParameterDetails = computed(()=> store.state.responseParameterDetails)

const props = defineProps({
  editResponse: Function
})

const formLabelWidth = '140px'


const sumbit = () => {
  if (
      responseParameterDetails.value.id === "" ||
      responseParameterDetails.value.name === "" ||
      responseParameterDetails.value.type === "" ||
      responseParameterDetails.value.description === ""
  ) {
    ElMessage.warning("参数属性不能为空")
    return;
  }
  if(props.editResponse!==undefined){
    ElMessage.success("修改成功")
    store.commit("showResponseParametersEditDialog",false)
    props.editResponse(responseParameterDetails.value)
    return;
  }
  // store.commit('showUserInfoDialog', false)
  console.log(responseParameterDetails.value)
  http.post("/interfaceInfo/admin/modifyInterfaceResponseParameters",responseParameterDetails.value).then(res=>{
    if(res.data.code === 200){
      ElMessage.success("修改成功")
      store.commit("showResponseParametersEditDialog",false)
    }
  })

}


</script>

<style>
.req .el-dialog__headerbtn{
  position: absolute;
  left: 100%!important;
  transform: translateX(-100%);
}
</style>

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