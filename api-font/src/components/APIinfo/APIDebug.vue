<template>
  <div>
    <div class="search-box">
      <div class="method">
        {{ props.method }}
      </div>
      <div class="input">
        <el-input
            disabled
            :value="props.url"
            class="w-50 m-2"
            size="large"
            placeholder="没有心仪的接口,快搜索一下吧"
        />
      </div>
      <div class="search" @click="call()">
        调用
      </div>
    </div>
    <div class="resParamDesc">
      请求参数设置：
    </div>
    <div class="tableBox">
      <table>
        <thead>
        <th style="width: 15vw">参数名称</th>
        <th style="width: 15vw">参数</th>
        <th style="width: 10vw">参数值</th>
        </thead>
        <tbody>
        <tr v-for="(item, index) in props.res" :key="index">
          <td>{{ item.description }}<span class="markMust">{{ item.is_required === 1 ? '*' : '' }} </span></td>
          <td>{{ item.name }}</td>
          <td v-if="params[index]" style="width: 25vw">
            <el-input
                v-model="params[index].default_value"
                :placeholder="item.description"
                show-word-limit
                style="width: 90%;"
                type="input"
            />
          </td>
        </tr>
        <tr v-if="!(props.res===undefined?0:props.res.length)">
          <td>暂无</td>
          <td>暂无</td>
          <td>暂无</td>
        </tr>
        </tbody>
      </table>
    </div>
    <div class="resParamDesc">
      返回结果：
    </div>
    <div class="code">
      <v-md-preview
          :text="'```json\n'+ JSON.stringify(json, null, 2)"
          @copy-code-success="handleCopyCodeSuccess">
      </v-md-preview>
    </div>
  </div>
</template>
<script setup>
import {defineProps, onMounted, ref} from "vue";
import http from "@/js/http";
import {ElMessage} from "element-plus";
import store from "@/store/store";

const props = defineProps({
  url: String,
  method: String,
  res: Array,
  interfaceId: String,
});

const params = ref([{default_value: ""}])

onMounted(() => {
  setTimeout(() => {
    params.value = props.res
    console.log("params", params.value)
  }, 1000)
})
const json = ref({})

const call = () => {
  let p = {}
  params.value.forEach((item) => {
    p[item.name] = item.default_value
  });
  http.post("/interfaceInfo/call", {
    method: props.method,
    params: p,
    url: props.url,
    interfaceId: props.interfaceId
  }).then((res) => {
    if (res.data.code === 200) {
      ElMessage.success("调用成功")
      store.commit("call");
      json.value = res.data.data
    } else {
      ElMessage.warning(res.data.data)
    }
  })
}

const handleCopyCodeSuccess = () => {
  ElMessage.success("复制成功")
}
</script>

<style lang="less" scoped>
@import "@/theme/style.less";

.search-box {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  height: 22vh;
  border-radius: 8px;
  background: rgb(255, 255, 255);

  .input {
    width: 40%;
  }

  .method {
    width: 90px;
    height: 40px;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 13px;
    cursor: pointer;
    font-weight: 600;
    color: white;
    background: @themeColor2;
    border-radius: 5px 0 0 5px;
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

.resParamDesc {
  padding: 2vh 14px;
  font-size: 14px;
  font-weight: 700;
  color: #555;
  height: 16px;
  line-height: 16px;
  border-left: #03e9f4 3px solid;
}

.tableBox {
  display: flex;
  align-items: center;
  justify-content: center;

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

.code {
}
</style>