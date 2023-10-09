<script setup>
import http from "@/js/http";
import router from "@/router/router";
import {ref} from 'vue';
import {ElMessage} from 'element-plus';
import { useStore } from 'vuex';

const store = useStore();

const user = ref({
  username: "",
  password: "",
  password2: ""
});

const register = () => {
  if (user.value.password !== user.value.password2) {
    ElMessage.error("两次密码不一致");
    return;
  }
  http.post(`/user/register`, user.value)
      .then(res => {
        console.log("/login", res);
        if (res.data.code === 200) {
          store.commit('updateUserInfo', {username: user.value.username, password: user.value.password})
          ElMessage.success("注册成功")
          router.push("/login");
        }
      })
      .catch(rea => {
        console.error("rea.data", rea.data);
      });
};
const goLogin = () => {
  console.log("goLogin");
  router.push("/login");
}
</script>

<template>
  <div class="body">
    <div class="loginBox">
      <h2>注册</h2>
      <div>
        <div class="item">
          <input type="text" required v-model="user.username">
          <label for="">用户名</label>
        </div>
        <div class="item">
          <input type="password" required v-model="user.password">
          <label for="">密码</label>
        </div>
        <div class="item">
          <input type="password" required v-model="user.password2">
          <label for="">确认密码</label>
        </div>
        <button class="btn" @click="register()">注册
          <span></span>
          <span></span>
          <span></span>
          <span></span>
        </button>
      </div>
      <div class="register-box">
        <div class="register">
          已经有了一个账户?, 去 <span class="goRegister" @click="goLogin()"> 登录</span>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.goRegister {
  display: inline-block;
  padding-left: 5px;
  color: #b003f4;
  cursor: pointer;
}

.register {
  display: flex;
  align-items: center;
  justify-content: center;
  position: absolute;
  width: 100%;
  bottom: -30px;
  left: 50%;
  transform: translateX(-50%);
  font-size: 12px;
}

.register-box {
  position: relative;
  height: 50px;
  width: 100%;
//background: #000!important;
}

* {
  margin: 0;
  padding: 0;
}

a {
  text-decoration: none;
}

input,
button {
  background: transparent;
  border: 0;
  outline: none;
}

.body {
  height: 100vh;
  width: 100vw;
  background: linear-gradient(#141e30, #243b55);
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 16px;
  color: #03e9f4;
}

.loginBox {
  position: relative;
  width: 400px;
  height: 464px;
  background-color: #0c1622;
  margin: 100px auto;
  border-radius: 10px;
  box-shadow: 0 15px 25px 0 rgba(0, 0, 0, .6);
  padding: 40px;
  box-sizing: border-box;
}

.captcha {
  position: absolute;
  transform: translate(260px, -90px);
  cursor: pointer;
}

h2 {
  text-align: center;
  color: aliceblue;
  margin-bottom: 30px;
  font-family: 'Courier New', Courier, monospace;
}

.item {
  height: 45px;
  border-bottom: 1px solid #fff;
  margin-bottom: 40px;
  position: relative;
}

.item input {
  width: 100%;
  height: 100%;
  color: #fff;
  padding-top: 20px;
  box-sizing: border-box;
}

.item input:focus + label,
.item input:valid + label {
  top: 0px;
  font-size: 2px;
}

.item label {
  position: absolute;
  left: 0;
  top: 12px;
  transition: all 0.5s linear;
}

.btn {
  padding: 10px 20px;
  margin-top: 30px;
  color: #03e9f4;
  position: relative;
  overflow: hidden;
  text-transform: uppercase;
  letter-spacing: 2px;
  left: 35%;
}

.btn:hover {
  border-radius: 5px;
  color: #fff;
  background: #03e9f4;
  box-shadow: 0 0 5px 0 #03e9f4,
  0 0 25px 0 #03e9f4,
  0 0 50px 0 #03e9f4,
  0 0 100px 0 #03e9f4;
  transition: all 1s linear;
}

.btn > span {
  position: absolute;
}

.btn > span:nth-child(1) {
  width: 100%;
  height: 2px;
  background: -webkit-linear-gradient(left, transparent, #03e9f4);
  left: -100%;
  top: 0px;
  animation: line1 1s linear infinite;
}

@keyframes line1 {

  50%,
  100% {
    left: 100%;
  }
}

.btn > span:nth-child(2) {
  width: 2px;
  height: 100%;
  background: -webkit-linear-gradient(top, transparent, #03e9f4);
  right: 0px;
  top: -100%;
  animation: line2 1s 0.25s linear infinite;
}

@keyframes line2 {

  50%,
  100% {
    top: 100%;
  }
}

.btn > span:nth-child(3) {
  width: 100%;
  height: 2px;
  background: -webkit-linear-gradient(left, #03e9f4, transparent);
  left: 100%;
  bottom: 0px;
  animation: line3 1s 0.75s linear infinite;
}

@keyframes line3 {

  50%,
  100% {
    left: -100%;
  }
}

.btn > span:nth-child(4) {
  width: 2px;
  height: 100%;
  background: -webkit-linear-gradient(top, transparent, #03e9f4);
  left: 0px;
  top: 100%;
  animation: line4 1s 1s linear infinite;
}

@keyframes line4 {

  50%,
  100% {
    top: -100%;
  }
}
</style>