<template>
  <ResponseParametersEdit :editResponse="editResponse"/>
  <div class="interface">
    <div class="table">
      <el-table :data="responseParameters" style="width: 100%" max-height="370">
        <el-table-column prop="name" label="参数名称" width="180"/>
        <el-table-column prop="type" label="参数类型" width="180"/>
        <el-table-column prop="description" label="参数描述" width="500"/>
        <el-table-column fixed="right" label="操作" min-width="180">
          <template #default="scope">
            <el-button
                link
                type="primary"
                size="small"
                @click.prevent="editRow(scope.$index)"
            >
              编辑
            </el-button>
            <el-button
                link
                type="primary"
                size="small"
                @click.prevent="deleteRow(scope.$index)"
            >
              移除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-button class="mt-4" style="width: 100%" @click="onAddItem"
      >新增参数
      </el-button
      >
    </div>
    <div class="next">
      <el-form-item>
        <el-button type="primary" @click="next(-1)" style="margin-right: auto;">&lt;&lt;</el-button>
        <el-button type="primary" @click="submit" style="margin-left: auto;">提交</el-button>
      </el-form-item>
    </div>
  </div>
</template>

<script setup>
import ResponseParametersEdit from "@/components/dialog/ResponseParametersEdit.vue";
import store from "@/store/store";
import {onMounted, ref, watch} from 'vue'
import {ElMessage} from "element-plus";
import http from "@/js/http";
import router from "@/router/router";

const responseParameters = ref([])
const current = ref(0)

onMounted(() => {
  responseParameters.value = store.state.responseParameters
})


watch(() => responseParameters.value, (newVal) => {
  store.commit("setResponseParameters", newVal)
}, {deep: true})


const editRow = (index) => {
  console.log(responseParameters.value[index])
  current.value = index
  store.commit("showResponseParametersEditDialog", true)
  store.commit("setResponseParameterDetails", responseParameters.value[index])
}

const deleteRow = (index) => {
  responseParameters.value.splice(index, 1)
}

const editResponse = (data) => {
  responseParameters.value[current.value] = data
}


const onAddItem = () => {
  responseParameters.value.push({
    name: '',
    type: '',
    description: '',
  })
}


const next = (int) => {
  store.commit('setActive', int);
}

const submit = () => {
  for (let i = 0; i < responseParameters.value.length; i++) {
    for (const key in responseParameters.value[i]) {
      if (responseParameters.value[i][key] === '') {
        ElMessage.warning("参数属性不能为空")
        return
      }
    }
  }
  // console.log("suubmit")
  console.log(store.state.requestParameters)
  console.log(store.state.responseParameters)
  console.log(store.state.interfaceAdd)
  http.post("/interfaceInfo/admin/saveInterfaceParameters",
      {
        ...store.state.interfaceAdd,
        requestParametersSaveRequests: store.state.requestParameters,
        responseParametersSaveRequests: store.state.responseParameters
      }
  ).then(res => {
    if (res.data.code === 200) {
      ElMessage.success("提交成功")
      store.commit("setRequestParameters", [])
      store.commit("setResponseParameters", [])
      store.commit("setInterfaceAdd", {})
      router.push("/admin/interfaceManager")
    }else {
      ElMessage.error("提交失败")
    }
  })


}
</script>

<style>
.el-dialog {
  max-width: 500px !important;
}
</style>

<style lang="less" scoped>
.interface {
  box-sizing: border-box;
  padding: 35px;
  width: 90%;
  margin: 0 auto;
  background: #ffffff;
  height: 75vh;
  border-radius: 8px;

  .table {
    height: 57vh;
  }

  .next {
    margin-top: 3vh;
  }
}
</style>