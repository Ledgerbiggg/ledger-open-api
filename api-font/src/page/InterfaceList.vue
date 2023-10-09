<template>
  <div>
    <div class="search-box">
      <div class="input">
        <el-input
            v-model="pageParams.search"
            class="w-50 m-2"
            size="large"
            placeholder="没有心仪的接口,快搜索一下吧"
        />
      </div>
      <div class="search" @click="changePage(1)">
        查询
      </div>
    </div>

    <div class="interface-box">
      <div class="interface" v-for="(item,index) in interfaceList" :key="index" @click="useInterface(item.id)">
        <div class="img" :title="item.name" :style="{backgroundImage:'url('+item.img_url+')'}">
          <div class="count" :title="'调用次数: '+item.call_count">{{ item.call_count }}</div>
        </div>
        <div class="text">
          {{ item.name }}
        </div>
        <div class="description">
          {{ item.description }}
        </div>
      </div>
      <div class="pageSelect">
        <el-pagination
            background
            layout="prev, pager, next"
            @current-change="changePage"
            :total="pageParams.total"/>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref} from 'vue'
import {onMounted} from "vue";
import http from "@/js/http";
import router from "@/router/router";

const pageParams = ref({
  pageNum: 1,
  pageSize: 10,
  total: 20,
  search: "",
})

const interfaceList = ref([])

onMounted(() => {
  changePage(1)
})

const useInterface = (id) => {
  router.push("/interface_info/" + id)
}
const changePage = (currentPage) => {
  pageParams.value.page = currentPage
  http.get("/interfaceInfo/getInterfaceList", pageParams.value).then(res => {
    console.log("interfaceInfo", res.data.data.records)
    interfaceList.value = res.data.data.records
    pageParams.value.total = res.data.data.total
    pageParams.value.pageSize = res.data.data.size
    pageParams.value.pageNum = res.data.data.current
    console.log(pageParams.value)
  })
}

</script>

<style scoped>
.search-box {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  height: 22vh;
  border-radius: 8px;
  background: rgb(255, 255, 255);

  .input {
    width: 60%;
  }

  .search {
    width: 90px;
    height: 40px;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 13px;
    cursor: pointer;
    font-weight: 600;
    color: white;
    background: rgba(5, 17, 236, 0.5);
    border-radius: 5px;
  }
}

.search-box:hover {
  box-shadow: 0px 0px 3px 3px rgba(89, 47, 47, 0.1);
  transition: all 0.5s;
}

.interface-box {
  margin-top: 3vh;
  width: 100%;
  height: 60vh;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: space-around;
  flex-wrap: wrap;
  overflow: auto;

  .interface {
    width: 18%;
    height: 35vh;
    margin: 1vh;
    border-radius: 8px;
    display: flex;
    align-items: center;
    justify-content: flex-start;
    flex-direction: column;
    background: #ffffff;

    .img {
      position: relative;
      width: 90px;
      height: 90px;
      margin: 20px;
      background: no-repeat center;
      background-size: 90px 90px;
      border-radius: 7px;

      .count {
        position: absolute;
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 10px;
        cursor: pointer;
        top: 0;
        left: 75px;
        height: 15px;
        min-width: 25px;
        border-radius: 10px;
        color: white;
        background: #ee0606;
      }
    }

    .text {
      margin-top: 10px;
      font-size: 14px;
      font-weight: 800;
      color: black;
    }

    .description {
      margin-top: 10px;
      font-size: 12px;
      color: black;
      text-align: center;
    }
  }

  .interface:hover {
    box-shadow: 0px 0px 6px 6px rgba(89, 47, 47, 0.1);
    transition: all .5s;
  }

  .pageSelect {
    position: absolute;
    bottom: 4vh;
    left: 85vw;
    transform: translateX(-50%);
  }
}
</style>