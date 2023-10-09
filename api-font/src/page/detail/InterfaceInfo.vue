<template>
  <div class="infobox">
    <div class="apiDetail">
      <div class="title">
        {{ interfaceDetail.name }}
      </div>
      <div class="detail">
        <div class="line">
          <div class="label">接口地址</div>
          <div class="context">{{ interfaceDetail.url }}</div>
        </div>
        <div class="line">
          <div class="label">返回格式</div>
          <div class="context">{{ interfaceDetail.resp_type }}</div>
        </div>
        <div class="line">
          <div class="label">消费积分</div>
          <div class="context">{{ interfaceDetail.consume + "个" }}</div>
        </div>
        <div class="line">
          <div class="label">请求方式</div>
          <div class="context "><span class="method">{{ interfaceDetail.method }}</span></div>
        </div>
        <div class="line">
          <div class="label">调用总次数</div>
          <div class="context">{{ interfaceDetail.call_count + "次" }}</div>
        </div>
        <div class="line">
          <div class="label">接口状态</div>
          <div class="context">{{ interfaceDetail.status === 1 ? "已上线" : "已下线" }}</div>
        </div>
        <div class="line">
          <div class="label">接口描述</div>
          <div class="context">{{ interfaceDetail.description }}</div>
        </div>
      </div>
    </div>
    <div class="doc-link">
      <div class="docTitle">接口详细描述请前往开发者在线文档查看：</div>
      <div class="docLink">
        <el-icon>
          <Document/>
        </el-icon>
        <a :href="docLink" target="_blank" class="Interface_online_documentation">
          接口在线文档：{{ interfaceDetail.name }}
        </a>
      </div>
    </div>
    <div class="useAPI">
      <el-tabs v-model="activeName" class="demo-tabs" @tab-click="handleClick">
        <el-tab-pane label="API文档" name="doc">
          <APIDoc
              :res="interfaceDetail.requestParametersList"
              :resp="interfaceDetail.responseParametersList"
              :example="interfaceDetail.example"
          />
        </el-tab-pane>
        <el-tab-pane label="在线调试工具" name="debug">
          <APIDebug
              :url="interfaceDetail.url"
              :method="interfaceDetail.method"
              :res="interfaceDetail.requestParametersList"
              :interfaceId="interfaceDetail.id"
          />
        </el-tab-pane>
        <el-tab-pane label="调用记录" name="errCode">
          <APICallHistory
              ref="APICallHistoryDom"
              :interfaceId="interfaceDetail.id"
          />
        </el-tab-pane>
        <el-tab-pane label="示例代码" name="example">
          <APIExample/>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script setup>
import {useRoute} from "vue-router";
import {computed, onMounted, ref, watch} from "vue";
import http from "@/js/http";

import APIDoc from "@/components/APIinfo/APIDoc.vue";
import APIDebug from "@/components/APIinfo/APIDebug.vue";
import APICallHistory from "@/components/APIinfo/APICallHistory.vue";
import APIExample from "@/components/APIinfo/APIExample.vue";
import store from "@/store/store";

const interfaceDetail = ref({})
const docLink = ref("https://www.baidu.com")
const activeName = ref("doc")
const APICallHistoryDom = ref(null);
const call = computed(() => {
  return store.state.call;
})

onMounted(() => {
  const route = useRoute(); // 使用useRoute()函数获取路由对象
  let id = route.params.id;
  http.get("/interfaceInfo/getInterfaceById", {id}).then(res => {
    interfaceDetail.value = res.data.data
  })
})

watch(call, () => {
  interfaceDetail.value.call_count++
})

const handleClick = () => {
  store.commit("changeTab")
}


</script>
<style lang="less" scoped>
@import "@/theme/style.less";

.infobox {
  height: 90vh;
  overflow: auto;
}

.apiDetail {
  width: 100%;
  height: 40vh;
  border-radius: 8px;
  margin-top: 5vh;
  background: #ffffff;

  .title {
    width: 97.5%;
    height: 8vh;
    border-bottom: rgba(190, 187, 187, 0.69) 1px solid;
    padding-left: 2vw;
    display: flex;
    align-items: center;
    font-size: 16px;
    font-weight: 600;
    color: #122f59;
  }

  .detail {
    display: flex;
    align-items: center;
    justify-content: space-between;
    flex-wrap: wrap;
  }

  .line {
    width: 33%;
    height: 8vh;
    display: flex;
    margin-top: 2vh;
    align-items: center;

    .label {
      color: rgba(0, 0, 0, 0.45);
      font-weight: normal;
      font-size: 14px;
      line-height: 1.5714285714285714;
      text-align: start;
      padding-left: 2vw;

    }

    .context {
      flex: 1;
      color: rgba(0, 0, 0, 0.88);
      font-size: 13px;
      line-height: 1.5714285714285714;
      word-break: break-word;
      overflow-wrap: break-word;
      padding-left: 1vw;

      .method {
        text-align: center;
        display: inline-block;
        background: @themeColor1;
        width: 35px;
        height: 20px;
        border-radius: 5px;
        font-weight: 600;
        color: white;
        border: #c003f4 solid 1px;
      }
    }
  }
}

.doc-link {
  margin-top: 5px;
  width: 100%;
  border-radius: 8px;
  height: 12vh;
  background: #fdfdfd;

  .docTitle {
    margin-left: 16px;
    padding: 2vh 14px;
    font-size: 14px;
    font-weight: 700;
    color: #555;
    height: 16px;
    line-height: 16px;
    border-left: #03e9f4 3px solid;
  }

  .Interface_online_documentation {
    display: inline-block;
    text-decoration: none;
    color: @themeColor1;
  }

  .docLink {
    margin-left: 16px;
    display: block;
    text-decoration: none !important;
    padding: 1vh 14px;
    font-size: 13px;
    font-weight: 700;
    height: 16px;
    line-height: 16px;
    color: #61c9ce;
    cursor: pointer;
  }

  .demo-tabs > .el-tabs__content {
    padding: 32px;
    color: #6b778c;
    font-size: 32px;
    font-weight: 600;
  }
}

.useAPI {
  width: 97.5%;
  border-radius: 8px;
  margin-top: 1vh;
  padding: 7px 16px;
  background: #ffffff;
}
</style>