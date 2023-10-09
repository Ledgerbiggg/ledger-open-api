<template>
  <div class="content-box">
    <div class="content">
      <h2>欢迎使用 ledger-API 接口开放平台 🎉</h2>
      <h3>ledger-API 接口开放平台是一个为用户和开发者提供全面API接口调用服务的平台 🛠</h3>
      <h4>😀
        作为用户您可以通过注册登录账户，获取接口调用权限，并根据自己的需求浏览和选择适合的接口。您可以在线进行接口调试，快速验证接口的功能和效果。</h4>
      <h4>💻 作为开发者 我们提供了
        <a class="link" :href="SDKLink" target="_blank">客户端SDK</a>，
        通过<a class="link" :href="voucherLink" target="_blank">开发者凭证</a>即可将轻松集成接口到您的项目中，实现更高效的开发和调用。
      </h4>
      <h4>🤝 您可以将自己的接口接入到ledger-API 接口开放平台平台上，并发布给其他用户使用。
        您可以管理和各个接口，以便更好地分析和优化接口性能。</h4>
      <h4>👌 我们还提供了<a class="link" :href="docLink" target="_blank">开发者在线文档</a>和技术支持，帮助您快速接入和发布接口。</h4>
      <h4>🏁 无论您是用户还是开发者，ledger-API
        接口开放平台都致力于提供稳定、安全、高效的接口调用服务，帮助您实现更快速、便捷的开发和调用体验。</h4>
      <div class="descriptions">
        <div class="box" v-for="(item,index) in boxList" :key="index">
          <div class="title">
            <span class="icon">{{ index + 1 }}</span>
            <div class="title-text">{{ item.title }}</div>
          </div>
          <div class="description">
            {{ item.description }}
          </div>
        </div>
      </div>
    </div>
  </div>

</template>

<script setup>
import {ref} from 'vue';
import {useStore} from "vuex";

const boxList = ref([
  {
    title: "多样化的接口选择",
    description: "平台上提供丰富多样的接口供您选择，涵盖了各个领域的功能和服务，满足不同需求。",
  },
  {
    title: "在线调试功能",
    description: "您可以在平台上进行接口在线调试，快速验证接口的功能和效果，节省了开发调试的时间和工作量。\n",
  },
  {
    title: "客户端SDK支持",
    description: "为了方便开发者集成接口到自己的代码中，平台提供了客户端SDK，使调用接口变得更加简单和便捷。",
  },
  {
    title: "开发者文档和技术支持",
    description: "平台提供了详细的开发者文档和技术支持，帮助开发者快速接入和发布接口，解决遇到的问题和困难。\n",
  },
  {
    title: "稳定和安全",
    description: "平台致力于提供稳定和安全的接口调用服务，采用了安全措施和技术手段，保障用户数据的安全性和隐私保护。",
  },
])

let store = useStore();
const SDKLink=store.state.linkList.SDKLink
const voucherLink=store.state.linkList.voucherLink
const docLink=store.state.linkList.docLink



</script>

<style lang="less" scoped>
@import "@/theme/style.less";
.link {
  cursor: pointer;
  color: #409eff;
  text-decoration: none;
}
h2{
  padding-top: 5vh;
}
.content-box {
  width:100%;
  ////background: url("https://image-bed-for-ledgerhhh.oss-cn-beijing.aliyuncs.com/image/20231002101721.png") no-repeat;
  //background-position: 100% 0%;
  background: rgb(255, 255, 255);
  background-size: 15%;
  .content{

    width: 90%;
    margin: 0 auto;
  }
  h3, h4 {
    margin-bottom: 1vh;
    padding: 0;
  }
}

.descriptions {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;

  .title {
    display: flex;
    align-items: center;
    justify-content: space-around;

    .icon {
      display: flex;
      align-items: center;
      justify-content: center;
      color: white;
      padding-left: 2px;
      font-weight: 600;
      width: 45px;
      height: 45px;
      background-image: url("https://gw.alipayobjects.com/zos/bmw-prod/daaf8d50-8e6d-4251-905d-676a24ddfa12.svg");
    }

    .title-text {
      flex: 1;
      font-size: 16px;
      font-weight: 800;
      padding-left: 10px;
    }
  }

  .box {
    background: @themeColor1;
    width: 28vh;
    height: 28vh;
    border-radius: 4%;
    box-shadow: 0px 0px 10px 5px rgba(0, 0, 0, 0.3); /* 添加阴影效果 */
    margin-bottom: 6vh;
    margin-top: 3vh;
  }

  .description {
    padding: 5px 18px;
    font-size: 14px;
    font-weight: 600;
  }

}


</style>
