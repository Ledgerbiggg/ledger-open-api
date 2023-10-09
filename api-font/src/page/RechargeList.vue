<template>
  <div class="recharge">
    <div class="wallet">
      <div class="my_wallet">我的钱包
        <span class="icon">
           <el-tooltip
               class="box-item"
               effect="dark"
               content="用于平台接口调用"
               placement="top"><el-icon>
          <Warning/>
        </el-icon>
      </el-tooltip>
        </span>
      </div>
      <div class="money">莱币:<span class="account">{{ my_wallet }}</span></div>
    </div>
    <div class="pointsMall">
      <div class="title">积分商城
        💰
      </div>
      <div class="mall">
        <div class="card"
             v-for="(item,index) in mallList"
             :key="index"
             @click="changGood(index)"
             :style="{background:currentGoodIndex===index?'rgba(0,255,247,0.32)':''}"
        >
          <div v-if="currentGoodIndex===index" class="mark"></div>
          <div class="shopTitle">
            <div class="num">
            <span class="quantity">
              💰<span>{{ item.count }} 莱币</span></span>
              <div class="price">¥{{ item.price }}</div>
            </div>
            <div class="description">
              增加{{ item.count }}莱币到钱包
            </div>
          </div>
          <div class="body">
            <div class="pic">
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
      </div>
    </div>
    <div class="buy">
      <div class="text">实付 <b>￥ {{ money }} 元</b></div>
      <div class="btn">
        <el-button type="primary" @click="buy">立即购买</el-button>
      </div>
    </div>

  </div>
</template>
<script setup>
import {onMounted, ref} from "vue";
import http from "@/js/http";
import {useRouter} from 'vue-router'
import {ElMessage} from "element-plus";
import store from "@/store/store";

const mallList = ref([])
const router = useRouter()
const currentGoodIndex = ref(-1)
const money = ref(0)
const my_wallet = ref(0)

onMounted(() => {
  getPriceList()
  getMyWallet()
})

const getMyWallet = () => {
  http.get("/order/myWallet").then(res => {
    // console.log("myWallet", res.data)
    my_wallet.value = res.data.data
  })
}

const getPriceList = () => {
  http.get("/order/priceList").then(res => {
    mallList.value = res.data.data
  })
}
const changGood = (index) => {
  money.value = mallList.value[index].price
  currentGoodIndex.value = index
}
const buy = () => {
  if (currentGoodIndex.value < 0) {
    ElMessage.error("请选择商品")
    return
  }
  http.post("/order/createOrder", {
    subject: mallList.value[currentGoodIndex.value].count + "莱币",
    price_id: mallList.value[currentGoodIndex.value].id
  }).then(res => {
    if (res.data.code === 200) {
      ElMessage.success("创建订单成功")
      store.commit("setOrder", {
        subject: mallList.value[currentGoodIndex.value].count,
        totalAmount: money.value,
        traceNo: res.data.data
      })
      router.push({
        path: "/orderDetail",
      })
    } else {
      ElMessage.error("创建订单失败,请联系站长")
    }
  })


}

</script>
<style lang="less" scoped>
.recharge {
  width: 100%;
  height: 87vh;
  background: #ffffff;
  display: flex;
  justify-content: flex-start;
  align-items: center;
  flex-direction: column;
  overflow: auto;

  .wallet {
    width: 95%;
    height: 16vh;
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

      .account {
        margin-left: 5px;
        color: #ef1313;
      }
    }

  }

  .pointsMall {
    width: 95%;
    border: rgba(12, 22, 34, 0.2) 1px solid;
    margin-left: 10px;
    border-radius: 8px;
    margin-top: 5vh;

    .title {
      font-weight: 600;
      display: flex;
      align-items: center;
      justify-content: flex-start;
      padding-inline: 24px;
      padding-block: 16px;
    }

    .mall {
      font-weight: 600;
      display: flex;
      align-items: center;
      justify-content: flex-start;
      flex-wrap: wrap;

      .card {
        position: relative;
        width: 200px;
        height: 280px;
        border-radius: 8px;
        border: rgba(12, 22, 34, 0.12) 1px solid;
        margin-left: 5vw;
        margin-top: 4vh;
        cursor: pointer;
        transition: all .5s;

        .mark {
          position: absolute;
          top: 0;
          left: 100%;
          transform: translateX(-100%);
          width: 0;
          height: 0;
          border-top: 12px solid blue; /* 左边边框透明 */
          border-right: 12px solid blue; /* 右边边框透明 */
          border-bottom: 12px solid transparent; /* 右边边框透明 */
          border-left: 12px solid transparent; /* 右边边框透明 */
          border-top-right-radius: 5px;
        }

        .shopTitle {
          height: 20%;
          margin-top: 10px;
          display: flex;
          justify-content: space-around;
          flex-direction: column;
          border-bottom: rgba(12, 22, 34, 0.15) 1px solid;
          padding: 0 20px;

          .num {
            display: flex;
            justify-content: space-between;
            align-items: center;

            .quantity {
              span {
                display: inline-block;
                transform: translateY(1.5px);
              }
            }

            .price {
              color: red;
            }
          }

          .description {
            font-size: 13px;
            font-weight: normal;
            color: #6b778c;
          }
        }

        .body {
          width: 100%;
          height: 190px;
          background: url("@/assets/img/money.jpg") no-repeat center;
          background-size: cover;
        }
      }

      .tip {
        width: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
        font-weight: normal;
        font-size: 14px;
        text-align: center;

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
    }
  }

  .buy {
    width: 91.5%;
    border: rgba(12, 22, 34, 0.2) 1px solid;
    margin-left: 10px;
    border-radius: 8px;
    margin-top: 2vh;
    padding-inline: 24px;
    padding-block: 26px;
    display: flex;
    justify-content: flex-end;
    align-items: flex-end;
    margin-bottom: 5vh;

    .text {
      display: flex;
      align-items: center;
      font-weight: 600;
      margin-right: 3vw;
      height: 100%;

      b {
        color: red;
      }
    }

    .btn {

    }
  }

}
</style>