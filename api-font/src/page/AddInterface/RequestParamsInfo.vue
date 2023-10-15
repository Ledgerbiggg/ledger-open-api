<template>
  <RequestParametersEdit :editRequest="editRequest"/>
  <div class="interface">
    <div class="table">
      <el-table :data="requestParameters" style="width: 100%" max-height="370">
        <el-table-column prop="name" label="参数名称" width="120"/>
        <el-table-column prop="is_required" label="是否必须" width="120"/>
        <el-table-column prop="type" label="参数类型" width="120"/>
        <el-table-column prop="description" label="参数描述" width="500"/>
        <el-table-column prop="default_value" label="默认值" width="120"/>
        <el-table-column fixed="right" label="操作" width="120">
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
        <el-button type="primary" @click="next(1)" style="margin-left: auto;">>></el-button>
      </el-form-item>
    </div>
  </div>
</template>

<script setup>
import RequestParametersEdit from "@/components/dialog/RequestParametersEdit.vue";
import store from "@/store/store";
import {onMounted, ref, watch} from 'vue'
import {ElMessage} from "element-plus";

const requestParameters = ref([])
const current=ref(0)

onMounted(()=>{
  requestParameters.value=store.state.requestParameters
})

watch(() => requestParameters.value, (newVal) => {
  store.commit("setRequestParameters", newVal)
}, { deep: true })


const editRow=(index) => {
  console.log(requestParameters.value[index])
  current.value=index
  store.commit("showRequestParametersEditDialog", true)
  store.commit("setRequestParameterDetails", requestParameters.value[index])
}

const deleteRow = (index) => {
  requestParameters.value.splice(index, 1)
}

const editRequest = (data) => {
  requestParameters.value[current.value] = data
}


const onAddItem = () => {
  requestParameters.value.push({
    name: '',
    is_required: '',
    type: '',
    description: '',
    default_value: '',
  })
}


const next = (int) => {
  for (let i = 0; i <requestParameters.value.length; i++) {
    for (const key in requestParameters.value[i]) {
      if (requestParameters.value[i][key] === '') {
        ElMessage.warning("参数属性不能为空")
        return
      }
    }
  }
  store.commit('setActive', int);
}
</script>

<style>
.el-dialog{
  max-width: 500px!important;
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