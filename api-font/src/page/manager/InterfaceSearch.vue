<template>
  <div class="interfaceSearchBox">
    <div class="searchBox">
      <div class="oneItem" v-for="(item,index) in searchList" :key="index">
        <div class=label>{{ item.label }}</div>
        <div class="input">
          <!-- 0=输入框  -->
          <el-input
              v-if="item.type===0"
              v-model="searchParams[item.model]"
              placeholder="请输入"/>
          <!-- 1=int  -->
          <el-input-number
              v-if="item.type===1"
              v-model="searchParams[item.model]"
              :min="1"
              :max="10"/>
          <!-- 1=double  -->
          <el-input-number
              v-if="item.type===2"
              v-model="searchParams[item.model]"
              class="mx-4"
              :min="0"
              :max="9999"
              controls-position="right"
          />
          <!-- 1=下拉  -->
          <el-cascader
              v-if="item.type===3"
              placeholder="请选择"
              :options="item.option"
              v-model="searchParams[item.model]"
              :show-all-levels="false"/>
        </div>

      </div>
      <div class="searchBtn">
        <el-button>重置</el-button>
        <el-button type="primary" @click="search">搜索</el-button>
      </div>
    </div>
  </div>

</template>
<script setup>
import {ref} from 'vue'
import store from "@/store/store";
import {ElMessage} from "element-plus";

const searchList = ref([
  {
    label: "接口id:",
    value: "",
    type: 0,
    model: "id",
    option: []
  },
  {
    label: "接口名称:",
    value: "",
    type: 0,
    model: "name",
    option: []
  },
  {
    label: "接口状态:",
    value: "",
    type: 3,
    model: "status",
    option: [
      {
        value: 1,
        label: '未启用',
      },
      {
        value: 2,
        label: '启用',
      }
    ]
  },
  {
    label: "接口消费金额:",
    value: 0,
    type:1,
    model: "consume",
    option: []
  },
  {
    label: "接口方法:",
    value: "",
    type: 3,
    model: "method",
    option: [
      {
        value: "GET",
        label: 'GET',
      },
      {
        value: "POST",
        label: 'POST',
      }
    ]
  },
])
const searchParams = ref({
  id: "",
  name: "",
  status: "",
  consume: "",
  method: ""
})
const search = () => {
  let param = deepClone(searchParams.value);
  for (let i = 0; i < searchList.value.length; i++) {
    let item = searchList.value[i]
    if(item.type===3){
      param[item.model] = getOne(param[item.model])
    }
  }
  param.status-=1
  console.log("param==========", param)

  store.commit("setSearchParams2", param)

  ElMessage.success("搜索成功")
}
const getOne=(arr)=>{
  if(arr.length !== 0 && arr[0]){
    return arr[0]
  }
}

const deepClone = (obj) => {
  if (typeof obj !== 'object' || obj === null) {
    return obj;
  }

  if (Array.isArray(obj)) {
    const clonedArray = [];
    for (let i = 0; i < obj.length; i++) {
      clonedArray[i] = deepClone(obj[i]);
    }
    return clonedArray;
  }

  const clonedObject = {};
  for (const key in obj) {
    if (Object.prototype.hasOwnProperty.call(obj, key)) { // 修复这里
      clonedObject[key] = deepClone(obj[key]);
    }
  }

  return clonedObject;
}


</script>
<style>
.el-cascader-menu__wrap.el-scrollbar__wrap {
  height: 120px;
}
.interfaceSearchBox .el-input-number__increase{
  margin-left: 100%;
  transform: translateX(-100%);
}
</style>
<style lang="less" scoped>
.interfaceSearchBox {
  width: 100%;
  height: 20vh;
  display: flex;
  align-items: center;
  background: #ffffff;
  border-radius: 8px;
  margin-bottom: 7vh;

  .searchBox {
    position: relative;
    width: 95%;
    display: flex;
    align-items: center;
    justify-content: flex-start;
    flex-wrap: wrap;

    .oneItem {
      display: flex;
      align-items: center;
      justify-content: flex-start;
      width: 350px;
      margin: 10px;

      .label {
        width: 30%;
        position: relative;
        display: inline-flex;
        align-items: center;
        max-width: 100%;
        height: 32px;
        color: rgba(0, 0, 0, 0.88);
        font-size: 14px;
        font-weight: 600;
        margin-right: 9px;

      }
    }

    .searchBtn {
      position: absolute;
      width: 138px;
      top: 70%;
      left: 100%;
      transform: translate(-100%, -50%);
    }
  }
}

</style>