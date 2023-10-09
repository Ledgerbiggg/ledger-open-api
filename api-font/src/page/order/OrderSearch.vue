<template>
  <div class="box">
    <div class="searchBox">
      <div class="oneItem" v-for="(item,index) in searchList" :key="index">
        <div class=label>{{ item.label }}</div>
        <div class="input">
          <el-input v-if="item.type===0" v-model="searchParams[item.model]" placeholder="请输入"/>
          <el-input-number
              v-if="item.type===2"
              v-model="searchParams[item.model]"
              class="mx-4"
              :min="0"
              :max="9999"
              controls-position="right"
          />
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
    label: "订单号:",
    value: "",
    type: 0,
    model: "traceNo",
    option: []
  },
  {
    label: "订单名称:",
    value: "",
    type: 0,
    model: "subject",
    option: []
  },
  {
    label: "支付金额:",
    value: 0,
    type: 2,
    model: "totalAmount",
    option: []
  },
  {
    label: "订单状态:",
    value: 1,
    type: 3,
    model: "orderState",
    option: [
      {
        value: 1,
        label: '全部订单',
      },
      {
        value: 2,
        label: '已支付',
      },
      {
        value: 3,
        label: '未支付',
      }
    ]
  },
])
const searchParams = ref({
  subject: "",
  traceNo: "",
  totalAmount: "",
  orderState: "",
})
const search = () => {
  console.log("searchParams.value.orderState", searchParams.value)
  let param = deepClone(searchParams.value);
  if (searchParams.value.orderState.length !== 0 && searchParams.value.orderState[0]) {
    switch (searchParams.value.orderState[0]) {
      case 1:  {
        param.orderState=""
        break
      }
      case 2: {
        param.orderState = true
        break
      }
      case 3: {
        param.orderState = false
        break
      }
    }
  }
  console.log("searchParams.value.orderState1111", param)

  store.commit("setSearchParams", param)

  ElMessage.success("搜索成功")
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
</style>
<style lang="less" scoped>
.box {
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