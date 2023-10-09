<template>
  <div>
    <div class="tableBox">
      <table>
        <thead>
        <th style="text-align: center;width: 15vw">调用时间</th>
        <th style="text-align: center;width: 10vw">调用结果</th>
        </thead>
        <tbody>
        <tr v-for="(item,index) in callHistoryList" :key="index">
          <td style="text-align: center">{{ item.time }}</td>
          <td @click="showDetail(item)" class="showDetail" style="text-align: center">查看</td>
        </tr>
        <tr v-if="!(callHistoryList===undefined?0:callHistoryList.length)">
          <td>暂无</td>
          <td>暂无</td>
        </tr>
        </tbody>
      </table>
    </div>
    <div class="pagination">
      <el-pagination
          background
          layout="prev, pager, next"
          :total="pageParams.total"
          @current-change="changePage"
      />
    </div>
    <div class="dialog">
      <DialogDraggable/>
    </div>
  </div>
</template>
<script setup>
import {computed, defineProps, onMounted, ref, watch} from "vue";
import http from "@/js/http";
import DialogDraggable from "@/components/dialog/DialogDraggable.vue";
import store from "@/store/store";

const pageParams = ref({
  pageNum: 1,
  pageSize: 10,
  total: 0
})
const props = defineProps({
  interfaceId: String,
});
const callHistoryList = ref([]);
const tab = computed(() => {
  //返回的是ref对象
  return store.state.tab;
})

onMounted(() => {
  setTimeout(() => {
    changePage(1)
  }, 700)
})

const changePage = (currentPage) => {
  console.log("currentPage", currentPage)
  http.get("/interfaceInfo/callHistory", {
    pageNum: currentPage,
    pageSize: pageParams.value.pageSize,
    total: pageParams.value.total,
    interfaceId: props.interfaceId
  }).then(res => {
    console.log("callHistory", res.data.data.records)
    pageParams.value.total = res.data.data.total
    pageParams.value.pageSize = res.data.data.size
    pageParams.value.pageNum = res.data.data.current
    callHistoryList.value = res.data.data.records
  })
}
const showDetail = (item) => {
  store.commit("showDetail", true)
  store.commit("callDetail", item)
}

watch(tab, () => {
  changePage(1)
})

</script>
<style lang="less" scoped>
.tableBox {
  display: flex;
  align-items: flex-start;
  justify-content: center;
  min-height: 75vh;

  table {
    margin: 10px;
    border-radius: 12px;
    overflow: hidden;
  }

  th {
    background: #FAFAFA;
    font-size: 15px;
    height: 6vh;
    font-weight: 700;
    padding-left: 0.3vw;
    text-align: left;
  }

  .showDetail {
    cursor: pointer;
  }

  td {
    font-size: 15px;
    height: 6vh;
    font-weight: 550;
    padding-left: 0.3vw;
    text-align: left;

    .markMust {
      margin-left: 3px;
      color: red;
    }
  }

  tr {
    border-bottom: rgba(119, 119, 119, 0.65) 5px solid;
  }

  tr:hover {
    transition: all .8s;
    background: rgba(180, 180, 180, 0.23);
  }
}

.pagination {
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>