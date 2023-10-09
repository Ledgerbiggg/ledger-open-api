<template>
  <div class="wallet">
    <div class="my_wallet">订单详情
      <span class="icon">
           <el-tooltip
               class="box-item"
               effect="dark"
               content="需要支付的订单信息"
               placement="top"><el-icon>
          <Warning/>
        </el-icon>
      </el-tooltip>
        </span>
    </div>
    <div class="money">莱币数量:<span class="account">{{ subject }} </span></div>
    <div class="money">莱币使用期限:<span class="account">无限制期限</span></div>
    <div class="money">支付总金额:<span class="account">{{ totalAmount }}元</span></div>
  </div>
  <div class="wallet payMethod">
    <div class="my_wallet">支付方式
    </div>
    <div class="money">
      <el-radio-group v-model="radio">
        <div class="payOptions">
          <el-radio :label="1">
            <div class="payOption">
              <div class="payIcon"></div>
              <div class="payName">支付宝</div>
            </div>
          </el-radio>
        </div>
      </el-radio-group>
    </div>
  </div>
  <div class="buy">
    <div class="text">实付 <b>￥ {{ totalAmount }} 元</b></div>
    <div class="btn">
      <el-button type="primary" :disabled="radio===-1 || paying" @click="buy">立即购买</el-button>
    </div>
  </div>
  <el-dialog
      v-model="showDialog"
      title="温馨提示"
      width="30%"
      align-center
      :show-close="false"
  >
    <span>请在新开启的页面完成支付</span>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="checkOrderPayState">
          我已支付订单
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>
<script setup>
import {ref, onMounted, watch} from 'vue';
import {useRouter} from "vue-router";
import http from "@/js/http";
import {ElMessage} from "element-plus";
import store from "@/store/store";

const subject = ref('');
const totalAmount = ref('');
const traceNo = ref('');
const radio = ref(-1)
const paying = ref(false)
const showDialog = ref(false)
const router = useRouter()

onMounted(() => {
  // 直接使用参数获取路由参数
  subject.value = store.state.order.subject;
  totalAmount.value = store.state.order.totalAmount;
  traceNo.value = store.state.order.traceNo;
});

watch(radio, (newValue, oldValue) => {
  console.log("newValue", newValue)
  console.log("oldValue", oldValue)
})

const buy = () => {
  paying.value = true;
  showDialog.value = true;
  const host = window.location.hostname;
  const protocol = window.location.protocol;
  const url = `${protocol}//${host}/api/order/pay?subject=${subject.value}&totalAmount=${totalAmount.value}&traceNo=${traceNo.value}`;
  window.open(url, '_blank');
}

const checkOrderPayState = () => {
  showDialog.value = false;
  paying.value = false;
  http.get("/order/checkOrderPayState", {orderId: traceNo.value}).then(res => {
    if (res.data.code !== 200) {
      return ElMessage.error("订单未支付")
    } else {
      ElMessage.success("订单已支付")
      router.push({
        path: "/recharge/list",
      })
    }

  })

}

</script>


<style lang="less" scoped>
.wallet {
  width: 95%;
  height: 39vh;
  border: rgba(12, 22, 34, 0.2) 1px solid;
  margin-left: 10px;
  border-radius: 8px;
  margin-top: 5vh;

  .my_wallet {
    font-weight: 600;
    display: flex;
    align-items: center;
    justify-content: flex-start;
    padding-inline: 24px;
    padding-block: 16px;
    border-bottom: rgba(12, 22, 34, 0.2) 1px solid;

    .icon {
      margin-top: 5px;
      margin-left: 5px;
    }
  }

  .money {
    font-weight: 600;
    display: flex;
    align-items: center;
    justify-content: flex-start;
    padding-inline: 24px;
    padding-block: 16px;
    font-size: 15px;

    .account {
      margin-left: 5px;
      color: #ef1313;
    }
  }
}

.payMethod {
  height: 29vh;

  .money {
    display: flex;
    align-items: center;
    justify-content: center;
    flex-wrap: wrap;
  }

  .payOptions {
    width: 250px;
    height: 100px;
    border-radius: 8px;
    display: flex;
    align-items: center;
    justify-content: center;
    background: #ffffff;

    .payOption {
      display: flex;
      align-items: center;
      justify-content: center;

      .payIcon {
        width: 50px;
        height: 50px;
        background: url("@/assets/img/payIcon.png") no-repeat;
        background-size: cover;
      }

      .payName {
        width: 60%;
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 16px;
        color: #070707;
        font-weight: 600;


      }
    }

  }

  .payOptions:hover {
    transition: all 0.5s;
    box-shadow: 0px 0px 6px 5px rgba(26, 20, 20, 0.15);
  }

}

.buy {
  width: 91.5%;
  border: rgba(12, 22, 34, 0.2) 1px solid;
  margin-left: 10px;
  border-radius: 8px;
  margin-top: 2vh;
  padding-inline: 24px;
  padding-block: 16px;
  display: flex;
  justify-content: flex-end;
  align-items: flex-end;
  margin-bottom: 5vh;

  .text {
    display: flex;
    align-items: center;
    font-weight: 600;
    transform: translateY(-7px);
    margin-right: 3vw;
    height: 100%;

    b {
      color: red;
    }

  }


  .btn {

  }
}

.dialog {
  position: fixed;
}
</style>
<style>
.el-radio {
  width: 100%;
  height: 100%;
  padding-left: 10%;

}

.el-radio__label {
  width: 100%;
}
</style>