<template>
  <div class="interface">
    <el-dialog v-model="store.state.InterfaceEditDialog" title="修改接口信息">
      <el-form :model="InterfaceDetails">
        <el-form-item label="名称:" :label-width="formLabelWidth">
          <el-input v-model="InterfaceDetails.name" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="状态" :label-width="formLabelWidth">
          <el-select v-model="InterfaceDetails.status" placeholder="请选择">
            <el-option label="否" :value=0 />
            <el-option label="是" :value=1 />
          </el-select>
        </el-form-item>
        <el-form-item label="描述:" :label-width="formLabelWidth">
          <el-input v-model="InterfaceDetails.description" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="地址:" :label-width="formLabelWidth">
          <el-input v-model="InterfaceDetails.url" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="图片地址:" :label-width="formLabelWidth">
          <el-input v-model="InterfaceDetails.img_url" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="方法" :label-width="formLabelWidth">
          <el-select v-model="InterfaceDetails.method" placeholder="请选择">
            <el-option label="GET" value="GET"/>
            <el-option label="POST" value="POST"/>
          </el-select>
        </el-form-item>
        <el-form-item label="响应类型" :label-width="formLabelWidth">
          <el-select v-model="InterfaceDetails.resp_type" placeholder="请选择">
            <el-option label="JSON" value="JSON"/>
            <el-option label="IMAGE" value="IMAGE"/>
          </el-select>
        </el-form-item>
        <el-form-item label="示例值:" :label-width="formLabelWidth">
          <el-input type="textarea" v-model="InterfaceDetails.example" autocomplete="off"/>
        </el-form-item>
      </el-form>

      <template #footer>
      <span class="dialog-footer">
        <el-button @click="store.commit('showInterfaceEditDialog',false)">取消</el-button>
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
import {deepClone} from "@/js/ObjectClone";
import store from "@/store/store";
import {ElMessage} from "element-plus";
import http from "@/js/http";

const InterfaceDetails = computed(()=> store.state.InterfaceDetails)


const formLabelWidth = '140px'

const sumbit = () => {
  console.log("submit")
  store.commit('showInterfaceEditDialog', false)
  let param= deepClone(InterfaceDetails.value)
  param.requestParametersVos=null
  param.responseParametersVos=null
  http.post("/interfaceInfo/admin/modifyInterfaceParameters",param).then(res=>{
    if(res.data.code === 200){
      ElMessage.success("修改成功")
      store.commit("showRequestParametersEditDialog",false)
    }
  })

}


</script>

<style>
.el-dialog{
  max-width: 500px!important;
}
.interface .el-dialog__headerbtn{
  position: absolute;
  left: 100%!important;
  transform: translateX(-100%);
}
.interface .el-textarea {
  height: 100px!important; /* 设置文本域的高度为200px，根据需要调整 */
  max-width: 300px!important;
}
.interface .el-textarea .el-textarea__inner{
  height: 100px!important; /* 设置文本域的高度为200px，根据需要调整 */
  max-width: 300px!important;
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