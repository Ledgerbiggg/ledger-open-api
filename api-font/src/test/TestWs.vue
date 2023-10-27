<template>
  <div>
    <input v-model=msg>
    <button @click="sendMsg">点击发送消息</button>
  </div>
</template>
<script setup>
import {ref} from "vue";

const msg = ref('')

// 创建WebSocket连接
const socket = new WebSocket("ws://localhost:9999/websocket/testWs"); // 替换成你要连接的WebSocket服务器地址

// 监听连接打开事件
socket.addEventListener("open", (event) => {
  console.log(event, "WebSocket连接已打开");
  // 在连接成功后发送消息
  socket.send("Hello, WebSocket!");
});

// 监听接收消息事件
socket.addEventListener("message", (event) => {
  const receivedData = event.data;
  console.log("接收到消息: " + receivedData);
});

// 监听连接关闭事件
socket.addEventListener("close", (event) => {
  if (event.wasClean) {
    console.log("WebSocket连接已关闭");
  } else {
    console.error("WebSocket连接意外关闭");
  }
});

// 监听连接错误事件
socket.addEventListener("error", (event) => {
  console.error(event, "WebSocket连接出现错误");
});


const sendMsg = () => {
  msg.value=""
  socket.send(msg.value);
}


</script>
<style scoped>

</style>