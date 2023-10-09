<template>
  <div class="resParamDesc">请求参数说明：</div>
  <div class="tableBox">
    <table>
      <thead>
      <th style="width: 15vw">参数名称</th>
      <th style="width: 10vw">必选</th>
      <th style="width: 10vw">类型</th>
      <th style="width: 15vw">描述</th>
      </thead>
      <tbody>
      <tr v-for="(item, index) in props.res" :key="index">
        <td>{{ item.name }} <span class="markMust">{{item.is_required === 1 ? '*' : ''}} </span></td>
        <td>{{ item.is_required===1?'是':'否' }}</td>
        <td>{{ item.type }}</td>
        <td>{{ item.description }}</td>
      </tr>
      <tr v-if="!(props.res===undefined?0:props.res.length)">
        <td>暂无</td>
        <td>暂无</td>
        <td>暂无</td>
        <td>暂无</td>
      </tr>
      </tbody>
    </table>
  </div>
  <div class="resParamDesc">响应参数说明：</div>
  <div class="tableBox">
    <table>
      <thead>
      <th style="width: 15vw">参数名称</th>
      <th style="width: 10vw">类型</th>
      <th style="width: 15vw">描述</th>
      </thead>
      <tbody>
      <tr v-for="(item, index) in props.resp" :key="index">
        <td>{{ item.name }}</td>
        <td>{{ item.type }}</td>
        <td>{{ item.description }}</td>
      </tr>
      <tr v-if="!(props.resp===undefined?0:props.resp.length)">
        <td>暂无</td>
        <td>暂无</td>
        <td>暂无</td>
      </tr>
      </tbody>
    </table>
  </div>
  <div class="resParamDesc">返回示例：</div>
  <v-md-preview :text="'```json\n'+props.example" @copy-code-success="handleCopyCodeSuccess"></v-md-preview>
</template>
<script setup>


import {defineProps} from 'vue';
import {onMounted} from "vue";
import {ElMessage} from 'element-plus';


const props = defineProps({
  res: Array,
  resp: Array,
  example: String,
});

onMounted(() => {
  console.log("props", props.res)
})

const handleCopyCodeSuccess = () => {
  ElMessage.success("复制成功")
}

</script>
<style lang="less" scoped>
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
    .markMust{
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

</style>