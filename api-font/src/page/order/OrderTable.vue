<template>
  <div>
    <!--    <el-button @click="resetDateFilter">reset date filter</el-button>-->
    <!--    <el-button @click="clearFilter">reset all filters</el-button>-->
    <el-table height="55vh" ref="tableRef" row-key="date" :data="tableData" style="width: 100%;overflow: hidden">
      <el-table-column align="center" prop="id" label="订单号" width="220">
        <template #default="scope">
          <div class="centered-tag">
            <span>{{ scope.row.id.slice(0, 10) }}...{{ scope.row.id.slice(25) }}</span>
          </div>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="subject" label="订单名称" width="120"/>
      <el-table-column align="center" prop="status" label="订单状态" width="120"/>
      <el-table-column
          align="center"
          prop="tag"
          label="支付类型"
          width="115"
          :filters="[
        { text: '微信', value: '微信' },
        { text: '支付宝', value: '支付宝' },
      ]"
          :filter-method="filterTag"
          filter-placement="bottom-end"
          :confirm-button-text="'确认'"
      >
        <template #default="scope">
          <div class="centered-tag">
            <el-tag
                :type="scope.row.tag === '支付宝' ? '' : 'success'"
                disable-transitions
            >{{ scope.row.tag }}
            </el-tag>
          </div>
        </template>
      </el-table-column>
      <el-table-column align="center" sortable prop="total_amount" label="订单金额" width="120"/>
      <el-table-column align="center" prop="count" label="增加积分数" width="120"/>
      <el-table-column align="center" prop="subject" label="订单描述" width="150"/>
      <el-table-column
          align="center"
          label="支付时间"
          width="110"
          column-key="date"
      >
        <template #default="scope">
          <div v-if="scope.row.trade_date_str">
            {{ scope.row.trade_date_str }}
          </div>
          <div v-else style="color: red;font-weight: 600">
            {{ '暂未支付' }}
          </div>
        </template>
      </el-table-column>
      <el-table-column
          align="center"
          prop="order_date_str"
          label="创建时间"
          sortable
          width="110"
          column-key="date"
      />
      <el-table-column
          align="center"
          label="操作" width="120">
        <template #default="scope">
          <!-- 这里是自定义的 HTML 内容 -->
          <span>
            <el-button
                size="small"
                type="primary"
                @click="handleEdit(scope.row)"
                :icon="View"/>
          </span>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
<script setup>
import {
  View,
} from '@element-plus/icons-vue'
import {computed, onMounted, ref, watch} from 'vue';
import http from "@/js/http";
import {useRouter} from "vue-router";
import store from "@/store/store";

const tableRef = ref();
const tableData = ref([])
const router = useRouter()
const searchParams = computed(() => {
  return store.state.search
})

onMounted(() => {
  getOrderList()
})


const getOrderList = (params) => {
  http.get("/order/getOrderList", params).then(res => {
    console.log("getOrderList", res.data.data)
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
  console.log("newVal.value", store.state.searchParams)
  let param = {}
  for (let newValKey in store.state.searchParams) {
    param[newValKey] = store.state.searchParams[newValKey]
  }
  getOrderList(param)
})

const handleEdit = (row) => {
  // 编辑按钮的点击事件处理逻辑
  console.log('编辑', row)
  router.push({
    path: '/orderDetailPage',
    query: row
  })
}


</script>
<style scoped>
.centered-tag {
  display: flex;
  justify-content: center; /* 水平居中对齐 */
  align-items: center; /* 垂直居中对齐 */
}
</style>