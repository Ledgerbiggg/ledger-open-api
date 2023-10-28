<template>
  <div>
    <input v-model=msg>
    <button @click="sendMsg">点击发送消息</button>
  </div>
</template>
<script setup>
import {ref} from "vue";
import tokenUtil from "@/js/tokenUtil";

const msg = ref('')

const wsLink = process.env.VUE_APP_API_WS_URL

console.log(wsLink)

const socket = new WebSocket(`${wsLink}${tokenUtil.getToken()}`);

socket.addEventListener("open", (event) => {
  console.log(event, "WebSocket连接已打开");
});

socket.addEventListener("message", (event) => {
  const receivedData = event.data;
  let parse = JSON.parse(receivedData);

  console.log("接收到消息: " , parse);





});

socket.addEventListener("close", (event) => {
  if (event.wasClean) {
    console.log("WebSocket连接已关闭");
  } else {
    console.error("WebSocket连接意外关闭");
  }
});

socket.addEventListener("error", (event) => {
  console.error(event, "WebSocket连接出现错误");
});



const sendMsg = () => {
  msg.value=""
  // let data={
  //   message:msg.value,
  //   toName:"kk"
  // }
  // socket.send(JSON.stringify(data));
}


</script>
<style scoped>

</style>