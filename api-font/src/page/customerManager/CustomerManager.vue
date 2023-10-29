<template>
  <div class="customerManager">
    <div class="left">
      <div class="search">
        <el-input
            v-model="searchName"
            class="w-50 m-2"
            placeholder="查找"
            @keyup.enter="search()"
            :suffix-icon="Search"
        />
      </div>
      <div class="nameList">
        <div class="name" v-for="(item,index) in nameList" @click="selectName(item)" :key="index">
          <div class="item">
            <img :src="item.url" class="avatar" ref="avatar"/>
            <div class="n">{{ item.name }}</div>
          </div>
        </div>
      </div>
    </div>
    <div class="center">
      <div class="title">
        {{ title }}
      </div>
      <div class="conversation" v-if="title" ref="scrollElement">
        <div v-for="(item,index) in conversation[title]"
             :key="index"
             class="item">
          <div class="time">
            {{item.formatDt}}
          </div>
          <div class="talk" v-if="item.username!=='kk'">
            <img :src="currentIcon.value" alt="null">
            <div class="talkContent">
              <div class="triangle"></div>
              {{ item.message }}
            </div>
          </div>
          <div class="reply" v-else>
            <div class="talkContent">
              <div class="triangle"></div>
              {{ item.message }}
            </div>
            <img src="@/assets/img/money.jpg" alt="null">
          </div>
        </div>
      </div>
      <div class="sendBox" v-if="title">
          <el-input
              v-model="message"
              class="w-50 m-2"
              size="large"
              placeholder="回复他"
              @keydown.enter="sendMessage"
          />
      </div>
    </div>
    <div class="right">
      <div>
        right
      </div>
    </div>
  </div>
</template>

<script setup>
import {nextTick, onMounted, reactive, ref} from "vue";
import {Search} from '@element-plus/icons-vue'
import ws from "@/js/ws.js";
import http from "@/js/http";
import uploadUtil from "@/js/uploadUtil";
import TimeUtil from "@/js/timeUtil";

const message=ref("")
const title = ref("");
const searchName = ref("");
const currentIcon=reactive({})
const scrollElement = ref(null);
const nameList = ref([

]);


const conversation = ref({
  "jj": {
    username: "jj",
    user_icon: "a4ab7c6a-d54f-493f-9085-8a515d8142e4.jpg",
    url: "",
    message: "你好",
    formatDt: "2022-12-12"
  },
})

onMounted(async () => {
  getAllSession()
})

const selectName=(item)=>{
  console.log(item.url)
  currentIcon.value=item.url
  title.value=item.name
}
const getAllSession=()=>{
  http.get("/customerService/getAllSession").then(async res => {
    let map = res.data.data;
    let keys = Object.keys(map);
    nameList.value = await Promise.all(keys.map(async i => {
      let url = await uploadUtil.upload(map[i][0].user_icon);
      return {
        name: i,
        url
      }
    }));
    console.log("nameList.value",nameList.value)
    conversation.value = map
  })
}


const listenMsg=async (data) => {
  console.log("收到的消息: ", data);
  let fromName = data.fromName;
  let message = data.message;
  let formatDt = data.formatDt;
  if (fromName !== "system") {
    nameList.value.forEach(i => {
      if (i.name === fromName) {
        conversation.value[fromName].push({
          username: fromName,
          user_icon: i.url,
          url: "",
          message,
          formatDt
        })
      }
    })
  }
  await nextTick();
  if(!scrollElement.value) return
  scrollElement.value.scrollTop = scrollElement.value.scrollHeight
}
const search = () => {
  console.log(name.value);
}
const sendMessage=async () => {
  if(!message.value) return
  ws.sendMsg(message.value, title.value)
  let value = message.value;
  conversation.value[title.value].push({
    username: "kk",
    message: value,
    formatDt: TimeUtil.getNowTimeFormat(),
  })
  message.value = ""
  await nextTick();
  if(!scrollElement.value) return
  scrollElement.value.scrollTop = scrollElement.value.scrollHeight
}
ws.initWebSocket();
ws.subscribeToMessages(listenMsg)

</script>


<style lang="less" scoped>
.customerManager {
  width: 100%;
  height: 85vh;
  background: #ffffff;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;

  .left {
    width: 25%;
    height: 100%;
    background: #105eee;

    .search {
      height: 8vh;
      display: flex;
      align-items: center;
      justify-content: center;
    }

    .nameList {
      .item {
        height: 8vh;
        background: #f81616;
        display: flex;
        align-items: center;
        justify-content: flex-start;

        img {
          height: 90%;
          padding: 0 15px;
        }
      }
    }
  }

  .center {
    width: 75%;
    height: 100%;
    background: #e81111;

    .title {
      height: 8vh;
      background: #001afd;
      display: flex;
      align-items: center;
      justify-content: flex-start;
      padding-left: 15px;
    }
    .conversation{
      height: 67vh;
      overflow: auto;
      background: #fd00d7;
      .item{
        min-height: 10%;
        background: rgba(4, 227, 248, 0.6);
        .time{
          height: 25%;
          font-size: 10px;
          color: #6b778c;
          display: flex;
          align-items: center;
          justify-content: center;
          background: rgba(29, 208, 180, 0.5);
        }
        .talk{
          min-height: 70%;
          display: flex;
          align-items: center;
          justify-content: flex-start;
          img{
            width: 30px;
          }
          .talkContent{
            position: relative;
            min-height: 40%;
            max-width: 80%;
            background: #ffffff;
            border-radius: 8px;
            padding: 10px;
            margin-left: 15px;
            font-size: 14px;

            .triangle {
              position: absolute;
              top: 50%;
              transform: translate(-100%,-50%);
              width: 0;
              height: 0;
              border-top: 5px solid transparent; /* 控制三角形高度 */
              border-bottom: 5px solid transparent; /* 控制三角形高度 */
              border-right: 5px solid #ffffff; /* 控制三角形宽度和颜色 */
            }
          }
        }
        .reply{
          min-height: 70%;
          display: flex;
          align-items: center;
          justify-content: flex-end;
          img{
            width: 30px;
          }
          .talkContent{
            position: relative;
            min-height: 40%;
            max-width: 80%;

            background: #08efd0;
            border-radius: 8px;
            padding: 10px;
            margin-right: 15px;
            font-size: 14px;

            .triangle {
              position: absolute;
              top: 50%;
              left: 100%;
              transform: translate(0,-50%);
              width: 0;
              height: 0;
              border-top: 5px solid transparent; /* 控制三角形高度 */
              border-bottom: 5px solid transparent; /* 控制三角形高度 */
              border-left: 5px solid #08efd0; /* 控制三角形宽度和颜色 */
            }

          }
        }
      }

    }
    .sendBox{
      height: 10vh;
      display: flex;
      align-items: center;
    }
  }

  .right {
    width: 25%;
    height: 100%;
    background: #e7c109;
  }


}
</style>