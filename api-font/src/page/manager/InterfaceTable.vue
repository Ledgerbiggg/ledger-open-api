<template>
  <div>
    <!--    <el-button @click="resetDateFilter">reset date filter</el-button>-->
    <!--    <el-button @click="clearFilter">reset all filters</el-button>-->
    <el-table height="55vh" ref="tableRef" row-key="date" :data="tableData" style="width: 100%;overflow: hidden">
      <el-table-column align="center" prop="id" label="接口号" width="160">
        <template #default="scope">
          <div class="centered-tag">
            <span>{{ scope.row.id.slice(0, 7) }}...{{ scope.row.id.slice(30) }}</span>
          </div>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="name" label="接口名称" width="160"/>
      <el-table-column align="center" prop="status" label="接口状态" width="120"/>
      <el-table-column align="center" prop="consume" label="接口消费" width="120"/>
      <el-table-column
          align="center"
          prop="tag"
          label="请求类型"
          width="115"
          :filters="[
        { text: 'GET', value: 'GET' },
        { text: 'POST', value: 'POST' },
      ]"
          :filter-method="filterTag"
          filter-placement="bottom-end"
          :confirm-button-text="'确认'"
      >
        <template #default="scope">
          <div class="centered-tag">
            <el-tag
                :type="scope.row.tag === 'GET' ? '' : 'success'"
                disable-transitions
            >{{ scope.row.tag }}
            </el-tag>
          </div>
        </template>
      </el-table-column>
      <el-table-column align="center" sortable prop="description" label="描述" width="120"/>
      <el-table-column align="center" prop="url" label="url" width="190"/>
      <el-table-column align="center" prop="call_count" label="请求次数" width="150"/>
      <el-table-column
          align="center"
          label="操作" width="170">
        <template #default="scope">
          <!-- 这里是自定义的 HTML 内容 -->
          <span class="btn">
            <el-button
                size="small"
                type="primary"
                @click="handleSelect(scope.row)"
                :icon="View"/>
          </span>
          <span class="btn">
            <el-button
                size="small"
                type="primary"
                @click="handleEdit(scope.row)"
                :icon="Edit"/>
          </span>
          <span class="btn">
            <el-button
                size="small"
                type="primary"
                @click="handleEdit(scope.row)"
                :icon="Delete"/>
          </span>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
<script setup>
import {
  View,
  Edit,
  Delete,
} from '@element-plus/icons-vue'
import {computed, onMounted, ref, watch} from 'vue';
import http from "@/js/http";
import {useRouter} from "vue-router";
import store from "@/store/store";

const tableRef = ref();
const tableData = ref([])
const router = useRouter()
const searchParams = computed(() => {
  return store.state.search2
})

onMounted(() => {
  getOrderList({})
})


const getOrderList = (params) => {
  console.log("params", params)
  Object.keys(params).forEach(key=>{
    if(!params[key]){
      if(params[key] !== 0){
        params[key] = null
      }
    }
  })
  console.log("params", params)
  http.get("/interfaceInfo/admin/getInterfaceList", params).then(res => {
    console.log("getInterfaceList", res.data.data)
    res.data.data.forEach(item=>{
      item.tag=item.method;
    })
    tableData.value = res.data.data
  })
};

const filterTag = (value, row) => {
  return row.tag === value;
};

// const filterHandler = (value, row, column) => {
//   const property = column.property;
//   return row[property] === value;
// };

watch(searchParams, () => {
  console.log("newVal.value2", store.state.searchParams2)
  let param = {}
  for (let newValKey in store.state.searchParams2) {
    param[newValKey] = store.state.searchParams2[newValKey]
  }
  getOrderList(param)
})

const handleEdit = (row) => {
  // 编辑按钮的点击事件处理逻辑
  router.push({
    path: '/admin/interfaceDetailPageEdit/'+ row.id,
  })
}

const handleSelect = (row) => {
  // 编辑按钮的点击事件处理逻辑
  // store.commit("setInterfaceDetail", row)
  router.push({
    path: '/admin/interfaceDetailPageSelect/'+ row.id,
  })
}


</script>
<style scoped>
.centered-tag {
  display: flex;
  justify-content: center; /* 水平居中对齐 */
  align-items: center; /* 垂直居中对齐 */
}
.btn{
  margin: 0 2px;
}
</style>
