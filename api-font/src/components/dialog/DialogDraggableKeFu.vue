<template>
  <div v-if="!isAdmin">
    <el-dialog v-model="store.state.showKeFuChat" width="60%" draggable>
      <template v-slot:title>
        <span>客服 </span>
        <span>(状态:</span>
        <span v-if="online"> 在线✅)</span>
        <span v-else>离线❌)</span>
      </template>
      <div class="conversation" ref="scrollElement">
        <div v-for="(item,index) in conversation"
             :key="index"
             class="item">
          <div class="time">
            {{ item.formatDt }}
          </div>
          <div class="talk" v-if="item.username==='kk'">
            <img src="@/assets/img/money.jpg" alt="null">
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
            <img :src="store.state.userIcon" alt="null">
          </div>
        </div>
      </div>
      <!--      <span>-->
      <!--          <v-md-preview :text="'```json\n'+store.state.callDetail.result" @copy-code-success="handleCopyCodeSuccess"></v-md-preview>-->
      <!--      </span>-->
      <template #footer>
      <span class="dialog-footer">
          <el-input
              v-model="msg"
              maxlength="50"
              placeholder="请输入对话内容"
              show-word-limit
              type="text"
              @keydown.enter="sendMessage"
          />
      </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import store from "@/store/store";
import {computed, nextTick, onMounted, ref} from "vue";
import ws from "@/js/ws.js";
import {ElMessage} from "element-plus";
import http from "@/js/http";
import TimeUtil from "@/js/timeUtil";

const scrollElement = ref(null)
const isAdmin = computed(() => localStorage.getItem('Role') === 'admin')
onMounted(() => {
  getMySeesion()
})
const online = ref(false)
const conversation = ref([
  {
    username: "kk",
    message: "你好,欢迎来到ledger-api平台",
    formatDt: TimeUtil.getNowTimeFormat(),
  }
])


const msg = ref('')

const getMySeesion = () => {
  http.get("/customerService/getMySession").then(res => {
    console.log("res.data.data", res.data.data)
    conversation.value = conversation.value.concat(res.data.data);
  })
}
const sendMessage = async () => {
  if(!msg.value) return
  ws.sendMsg(msg.value, "kk")
  conversation.value.push({
    username: "",
    message: msg.value,
    formatDt: TimeUtil.getNowTimeFormat(),
  })
  ElMessage.success("发送成功")
  msg.value = ''
  await nextTick();
  if(!scrollElement.value) return
  scrollElement.value.scrollTop = scrollElement.value.scrollHeight
}
const getMessage = async (data) => {
  // 创建第一次连接的时候看客服是不是在线
  console.log("data",data)
  if (data.kk && data.fromName === "system") {
    ElMessage.success("客服在线")
    online.value = true
  } else if (!data.kk && data.fromName === "system") {
    ElMessage.warning("客服不在线哦")
    online.value = false
  } else {
    data.username = "kk"
    conversation.value.push(data)
  }
  await nextTick();
  if(!scrollElement.value) return
  scrollElement.value.scrollTop = scrollElement.value.scrollHeight
}
if (!isAdmin.value) {
  ws.initWebSocket()
  ws.subscribeToMessages(getMessage)
}
</script>


<style lang="less" scoped>
.conversation {
  height: 50vh;
  overflow: auto;
  background: #EDEDED;
  border-radius: 5px;

  .item {
    min-height: 10%;
    padding: 15px;

    .time {
      height: 25%;
      font-size: 10px;
      color: #6b778c;
      display: flex;
      align-items: center;
      justify-content: center;
    }

    .talk {
      min-height: 70%;
      display: flex;
      align-items: center;
      justify-content: flex-start;

      img {
        width: 30px;
      }

      .talkContent {
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
          transform: translate(-100%, -50%);
          width: 0;
          height: 0;
          border-top: 5px solid transparent; /* 控制三角形高度 */
          border-bottom: 5px solid transparent; /* 控制三角形高度 */
          border-right: 5px solid #ffffff; /* 控制三角形宽度和颜色 */
        }
      }
    }

    .reply {
      min-height: 70%;
      display: flex;
      align-items: center;
      justify-content: flex-end;

      img {
        width: 30px;
      }

      .talkContent {
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
          transform: translate(0, -50%);
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
</style>