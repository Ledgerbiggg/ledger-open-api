<template>
  <div class="req">
    <el-dialog v-model="store.state.requestParametersEditDialog" title="修改请求参数">
      <el-form :model="requestParameterDetails">
        <el-form-item label="名称:" :label-width="formLabelWidth">
          <el-input v-model="requestParameterDetails.name" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="是否必须" :label-width="formLabelWidth">
          <el-select v-model="requestParameterDetails.is_required" placeholder="请选择">
            <el-option label="否" :value=0 />
            <el-option label="是" :value=1 />
          </el-select>
        </el-form-item>
        <el-form-item label="类型" :label-width="formLabelWidth">
          <el-select v-model="requestParameterDetails.type" placeholder="请选择">
            <el-option label="string" value="string"/>
            <el-option label="int" value="int"/>
            <el-option label="double" value="double"/>
            <el-option label="boolean" value="boolean"/>
            <el-option label="array" value="array"/>
          </el-select>
        </el-form-item>
        <el-form-item label="描述:" :label-width="formLabelWidth">
          <el-input v-model="requestParameterDetails.description" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="默认值:" :label-width="formLabelWidth">
          <el-input v-model="requestParameterDetails.default_value" autocomplete="off"/>
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="store.commit('showRequestParametersEditDialog',false)">取消</el-button>
        <el-button type="primary" @click="sumbit()">
          确认
        </el-button>
      </span>
      </template>
    </el-dialog>

  </div>
</template>


<script setup>
import {computed} from 'vue'
import store from "@/store/store";
import http from "@/js/http";
import {ElMessage} from "element-plus";
import { defineProps } from 'vue';
// import http from "@/js/http";

const props = defineProps({
  editRequest: Function
})

const requestParameterDetails = computed(()=> store.state.requestParameterDetails)


const formLabelWidth = '140px'


const sumbit = () => {
  console.log(requestParameterDetails.value)
  if (
      requestParameterDetails.value.id === "" ||
      requestParameterDetails.value.name === "" ||
      requestParameterDetails.value.is_required === "" ||
      requestParameterDetails.value.description === "" ||
      requestParameterDetails.value.default_value === "" ||
      requestParameterDetails.value.type === ""
  ) {
    ElMessage.warning("参数属性不能为空")
    return;
  }
  if(props.editRequest!==undefined){
    ElMessage.success("修改成功")
    store.commit("showRequestParametersEditDialog",false)
    props.editRequest(requestParameterDetails.value)
    return;
  }
  http.post("/interfaceInfo/admin/modifyInterfaceRequestParameters",requestParameterDetails.value).then(res=>{
    if(res.data.code === 200){
      ElMessage.success("修改成功")
      store.commit("showRequestParametersEditDialog",false)
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