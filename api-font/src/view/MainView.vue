<template>
  <div>
    <div class="tab-box">
      <img src="https://image-bed-for-ledgerhhh.oss-cn-beijing.aliyuncs.com/image/f898326080e5beae1f061950b308fc9.jpg">
      <div class="title">ledger-API 接口开放平台</div>
      <div class="options">
        <div
            v-show="item.tag.includes(role)"
            class="option"
            v-for="(item,index)
            in optionList"
            :key="index"
            @click="goRouter(item.router)">
          <el-icon>
            <component :is="item.componentName"></component>
          </el-icon>
          <div class="text">{{ item.text }}</div>
        </div>
      </div>
      <div class="document">
        <el-icon>
          <Tickets/>
        </el-icon>
        <div class="doc"><a :href="docLink" class="doc-link" target="_blank">开发者文档</a></div>
      </div>
      <div class="avatar">
        <el-avatar :size="50"
                   src="https://image-bed-for-ledgerhhh.oss-cn-beijing.aliyuncs.com/image/f898326080e5beae1f061950b308fc9.jpg"/>
      </div>
    </div>
    <div class="content-box">
      <router-view>
      </router-view>
    </div>

  </div>
</template>
<script setup>
import router from "@/router/router";
import {onMounted, ref} from 'vue';
import {useStore} from "vuex";

const role = ref("")

const optionList = ref([
  {
    router: "/welcome",
    text: "欢迎",
    componentName: "Sunny",
    tag: ["tourist", "admin"],
  },
  {
    router: "/interface/list",
    text: "接口广场",
    componentName: "SoldOut",
    tag: ["tourist", "admin"],
  },
  {
    router: "/recharge/list",
    text: "积分商城",
    componentName: "ShoppingCart",
    tag: ["tourist", "admin"],
  },
  {
    router: "/order/list",
    text: "我的订单",
    componentName: "ShoppingCart",
    tag: ["tourist", "admin"],
  },
  {
    router: "/admin/interfaceManager",
    text: "接口管理",
    componentName: "Avatar",
    tag: ["admin"],
  },
])
let store = useStore();
const docLink = store.state.linkList.docLink

onMounted(() => {
  let item = window.localStorage.getItem('Role');
  store.commit('setRole', item);
  role.value = item
})

const goRouter = (path) => {
  router.push(path)
}

</script>
<style lang="less" scoped>

@import "@/theme/style.less";

.doc-link {
  text-decoration: none;
  color: #0c1622;
}

.tab-box {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: flex-start;
  width: 90vw;
  height: 8vh;
  margin-left: 3vw;
  margin-bottom: 10px;
  border-bottom: #c4c0c0 1px solid;

  img {
    height: 5vh;
  }

  .title {
    font-weight: 600;
    font-size: 16px;
    color: rgba(0, 0, 0, 0.88);
    margin-left: 1vw;
  }

  .options {
    display: flex;
    align-items: center;
    justify-content: center;
    margin-left: 1vw;
    height: 100%;

    .option {
      display: flex;
      align-items: center;
      justify-content: center;
      width: 6vw;
      height: 100%;
      cursor: pointer;
      border-radius: 5%;
      margin: 5px;
    }

    .option:hover {
      background: rgba(0, 0, 0, 0.04);
    }

    .text {
      margin-left: 5px;
      font-size: 10px;
    }
  }

  .document {
    position: absolute;
    display: flex;
    align-items: center;
    justify-content: center;
    height: 100%;
    top: 0;
    left: 80vw;
    width: 200px;
    font-size: 18px;
    cursor: pointer;

    .doc {
      margin-left: 8px;
      font-size: 14px;
      font-weight: 800;

    }
  }

  .avatar {
    position: absolute;
    display: flex;
    align-items: center;
    justify-content: center;
    height: 100%;
    top: 0;
    left: 91vw;
    width: 70px;
    font-size: 18px;
  }

  .avatar:hover {
    background: rgba(0, 0, 0, 0.04);
  }
}

.content-box {
  width: 85%;
  margin: 0 auto;
  border-radius: 8px;
}

.dialog {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}
</style>