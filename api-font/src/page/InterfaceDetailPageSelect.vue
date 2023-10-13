<template>
<div style="height: 90vh;overflow: auto">
  <div class="apiDetail">
    <div class="title">
      接口详情
    </div>
    <div class="detail">
      <div class="line">
        <div class="label">接口名称</div>
        <div class="context">{{ interfaceDetail.name }}</div>
      </div>
      <div class="line">
        <div class="label">接口消费</div>
        <div class="context">{{ interfaceDetail.consume }}</div>
      </div>
      <div class="line">
        <div class="label">url</div>
        <div class="context">{{ interfaceDetail.url }}</div>
      </div>

      <div class="line">
        <div class="label">调用次数</div>
        <div class="context">{{interfaceDetail.call_count}}</div>
      </div>
      <div class="line">
        <div class="label">请求体格式</div>
        <div class="context">
          <el-tag class="ml-2"
                  :type="interfaceDetail.resp_type==='JSON' ? '' : 'success'">
            {{ interfaceDetail.resp_type }}
          </el-tag>
        </div>
      </div>
      <div class="line">
        <div class="label">接口id</div>
        <div class="context">{{ interfaceDetail.id }}</div>
      </div>
      <div class="line">
        <div class="label">调用方法</div>
        <div class="context ">
          <el-tag class="ml-2"
                  :type="interfaceDetail.tag==='GET' ? '' : 'success'">
            {{ interfaceDetail.method }}
          </el-tag>
        </div>
      </div>
      <div class="line">
        <div class="label">接口图片</div>
        <div class="context"><img width="80" :src="interfaceDetail.img_url" alt="图片出错"/></div>
      </div>
      <div class="line">
        <div class="label">示例</div>
        <div class="context">
          <v-md-preview
                  :text="'```json\n'+ JSON.stringify(interfaceDetail.example, null, 2)"
                  @copy-code-success="handleCopyCodeSuccess">
          </v-md-preview>
        </div>
      </div>
    </div>
  </div>
  <div class="apiDetail2">
    <div class="title">
      请求参数详情
    </div>
    <div class="table">
      <el-table :data="interfaceDetail.requestParametersVos" style="width: 100%" :max-height="200">
        <el-table-column label="名称" width="180">
          <template #default="scope">
            <div style="display: flex; align-items: center">
              <span style="">{{ scope.row.name }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="是否必须" width="180">
          <template #default="scope">
            <div style="display: flex; align-items: center">
              <span style="">{{ scope.row.is_required }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="类型" width="180">
          <template #default="scope">
            <div style="display: flex; align-items: center">
              <span style="">{{ scope.row.type }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="描述" width="180">
          <template #default="scope">
            <div style="display: flex; align-items: center">
              <span style="">{{ scope.row.description }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="默认值" width="180">
          <template #default="scope">
            <div style="display: flex; align-items: center">
              <span style="">{{ scope.row.default_value }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="Operations">
          <template #default="scope">
            <el-button size="small" @click="handleEdit(scope.$index, scope.row)"
            >Edit</el-button
            >
            <el-button
                    size="small"
                    type="danger"
                    @click="handleDelete(scope.$index, scope.row)"
            >Delete</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
  <div class="apiDetail3">
    <div class="title">
      响应参数详情
    </div>
    <div class="table">
      <el-table :data="interfaceDetail.responseParametersVos" style="width: 100%" :max-height="200">
        <el-table-column label="名称" width="180">
          <template #default="scope">
            <div style="display: flex; align-items: center">
              <span style="">{{ scope.row.name }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="是否必须" width="180">
          <template #default="scope">
            <div style="display: flex; align-items: center">
              <span style="">{{ scope.row.type }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="类型" width="180">
          <template #default="scope">
            <div style="display: flex; align-items: center">
              <span style="">{{ scope.row.description }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="Operations">
          <template #default="scope">
            <el-button size="small" @click="handleEdit(scope.$index, scope.row)"
            >Edit</el-button
            >
            <el-button
                    size="small"
                    type="danger"
                    @click="handleDelete(scope.$index, scope.row)"
            >Delete</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
  <div class="tip">
    本商品为虚拟内容,用于平台接口调用,购买后不支持<span>退换</span>。确认支付表示您已阅读并接受
    <a :href="store.state.linkList.userAgreement" target="_blank">用户协议</a>
    ，如付款成功后10分钟后未到账，
    请联系站长微信：
    <el-tooltip
            placement="top"
            content="<img src='https://image-bed-for-ledgerhhh.oss-cn-beijing.aliyuncs.com/image/20231004122537.png' width='100px' height='100px'/> "
            raw-content
    >
      <b>ledgerbiggg</b>
    </el-tooltip>
  </div>

</div>
</template>
<script setup>
import {onMounted, ref} from "vue";
import {ElMessage} from "element-plus";
import {useRoute} from "vue-router";
import http from "@/js/http";
import store from "@/store/store";


const handleEdit = (index, row) => {
  console.log(index, row);
};

const handleDelete = (index, row) => {
  console.log(index, row);
};



const interfaceDetail = ref({
  requestParametersVos:[],
  responseParametersVos:[]
})
const route = useRoute()

onMounted(() => {

  http.get("/interfaceInfo/admin/getInterfaceDetailById",{id:route.params.id}).then(res=>{
    console.log("getInterfaceDetailById",res.data)
    interfaceDetail.value=res.data.data
  })

})

const handleCopyCodeSuccess=()=>{
  ElMessage.success("复制成功")
}



</script>
<style>
.apiDetail .github-markdown-body{
  padding:24px 0 0 0;
  width: 290px;
}
</style>
<style lang="less" scoped>
@import "@/theme/style.less";
.apiDetail3{
  width: 100%;
  min-height: 10vh;
  border-radius: 8px;
  margin-top: 5vh;
  background: #ffffff;
  padding-bottom: 5vh;
  .title {
    position: relative;
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
  .table{
    padding-left: 2vw;
    margin-top: 2vh;
  }
}

.apiDetail2{
  width: 100%;
  min-height: 10vh;
  border-radius: 8px;
  margin-top: 5vh;
  background: #ffffff;
  padding-bottom: 5vh;
  .title {
    position: relative;
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
  .table{
    padding-left: 2vw;
    margin-top: 2vh;
  }
}
.apiDetail {
  width: 100%;
  height: 45vh;
  border-radius: 8px;
  margin-top: 5vh;
  background: #ffffff;

  .title {
    position: relative;
    width: 97.5%;
    height: 8vh;
    border-bottom: rgba(190, 187, 187, 0.69) 1px solid;
    padding-left: 2vw;
    display: flex;
    align-items: center;
    font-size: 16px;
    font-weight: 600;
    color: #122f59;

    .goPayment {
      position: absolute;
      top: 20%;
      left: 97%;
      transform: translateX(-100%);
    }

    .text {
      font-weight: 600;
      font-size: 15px;
    }
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
      width: 17%;
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

.tip {
  width: 100%;
  margin-top: 5vh;
  display: flex;
  justify-content: center;
  align-items: center;
  font-weight: normal;
  font-size: 14px;
  text-align: center;
  background: #ffffff;
  height: 10vh;

  span {
    font-weight: 600;
    color: red;
  }

  a {
    margin-left: 5px;
    text-decoration: none;
    color: blue;
  }

  b {
    cursor: pointer;
  }
}
</style>
