<template>
  <div class="apiDetail">
    <div class="title">
      订单详情
      <span class="goPayment" v-if="interfaceDetail.status==='未支付'">
        <el-button type="primary" @click="goPayment">
          <div class="text">
            去支付
          </div>
        </el-button>
      </span>
    </div>
    <div class="detail">
      <div class="line">
        <div class="label">订单名称</div>
        <div class="context">{{ interfaceDetail.subject }}</div>
      </div>
      <div class="line">
        <div class="label">订单金额(元)</div>
        <div class="context">{{ interfaceDetail.total_amount }}</div>
      </div>
      <div class="line">
        <div class="label">支付类型</div>
        <div class="context ">
          <el-tag class="ml-2"
                  :type="interfaceDetail.tag==='未选择支付方式' ? 'info' :
                  interfaceDetail.tag==='支付宝' ? '' : 'success'">
            {{ interfaceDetail.tag }}
          </el-tag>
        </div>
      </div>
      <div class="line">
        <div class="label">订单状态</div>
        <div class="context">
          <el-tag class="ml-2" :type="interfaceDetail.status==='未支付'?'danger':'success'">
            {{ interfaceDetail.status }}
          </el-tag>
        </div>
      </div>
      <div class="line">
        <div class="label">增加积分数</div>
        <div class="context">{{ interfaceDetail.count }}</div>
      </div>
      <div class="line">
        <div class="label">支付时间</div>
        <div class="context">{{ interfaceDetail.trade_date_str ? interfaceDetail.trade_date_str : '暂无' }}</div>
      </div>
      <div class="line">
        <div class="label">创建时间</div>
        <div class="context">{{ interfaceDetail.order_date_str }}</div>
      </div>
      <div class="line">
        <div class="label">商品描述</div>
        <div class="context">{{ interfaceDetail.subject }}</div>
      </div>
      <div class="line">
        <div class="label">订单号</div>
        <div class="context">{{ interfaceDetail.id }}</div>
      </div>
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

</template>
<script setup>
import {onMounted, ref} from "vue";
import {useRoute} from "vue-router";
import store from "@/store/store";
import {useRouter} from "vue-router";

const interfaceDetail = ref({})
const router = useRouter()
const route = useRoute()

onMounted(() => {
  interfaceDetail.value = route.query
})

const goPayment=()=>{
  store.commit("setOrder",{
    subject: interfaceDetail.value.subject,
    totalAmount: interfaceDetail.value.total_amount,
    traceNo: interfaceDetail.value.id
  })
  router.push({
    name: "OrderDetail",
  })
}


</script>
<style lang="less" scoped>
@import "@/theme/style.less";

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
    .text{
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