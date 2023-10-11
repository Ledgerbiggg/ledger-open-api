<template>
    <div class="recharge">
        <div class="wallet">
            <div class="my_wallet">
                个人信息设置
                <span class="icon">
           <el-tooltip
                   class="box-item"
                   effect="dark"
                   content="个人信息修改"
                   placement="top"><el-icon>
          <Warning/>
        </el-icon>
      </el-tooltip>
        </span>
            </div>
            <div class="avatarBox">
                <el-upload
                        class="avatar-uploader"
                        action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15"
                        :show-file-list="false"
                        :on-success="handleAvatarSuccess"
                        :before-upload="beforeAvatarUpload"
                >
                    <img v-if="imageUrl" :src="imageUrl" class="avatar" />
                    <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
                </el-upload>
            </div>
            <div class="money">昵称:<span class="account">{{ account.nickName }}</span></div>
            <div class="money">我的邀请码:<span class="account">{{ account.myInvitationCode }}</span></div>
            <div class="money">我的id:<span class="account">{{ account.myId }}</span></div>
            <div class="money">我的邮箱:<span class="account">{{ account.myMailBox }}</span></div>
        </div>
        <div class="wallet2">
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
        <div class="wallet3">
            <div class="my_wallet">
                开发者凭证（调用接口的凭证）
                <span class="icon">
           <el-tooltip
                   class="box-item"
                   effect="dark"
                   content="用于接口调用的凭证"
                   placement="top"><el-icon>
          <Warning/>
        </el-icon>
      </el-tooltip>
        </span>
            </div>
            <div class="money">AccessKey:<span class="account">{{ my_wallet }}</span></div>
            <div class="money">AccessKey:<span class="account">{{ my_wallet }}</span></div>
        </div>
    </div>
</template>

<script setup>
  import { ref } from 'vue'
  import { ElMessage } from 'element-plus'
  import { Plus } from '@element-plus/icons-vue'

  const imageUrl = ref('')

  const handleAvatarSuccess = (response, uploadFile) => {
    imageUrl.value = URL.createObjectURL(uploadFile.raw)
  }

  const beforeAvatarUpload = (rawFile) => {
    if (rawFile.type !== 'image/jpeg') {
      ElMessage.error('Avatar picture must be JPG format!')
      return false
    } else if (rawFile.size / 1024 / 1024 > 2) {
      ElMessage.error('Avatar picture size can not exceed 2MB!')
      return false
    }
    return true
  }

  const account = ref({
    nickName: "",
    myInvitationCode: "",
    myId: "",
    myMailBox: "",
  })
  const my_wallet = ref(15)


</script>
<style>
    .avatar-uploader .el-upload {
        border: 1px dashed var(--el-border-color);
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
        transition: var(--el-transition-duration-fast);
    }

    .avatar-uploader .el-upload:hover {
        border-color: var(--el-color-primary);
    }

    .el-icon.avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 178px;
        height: 178px;
        text-align: center;
    }
</style>
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
            height: 42vh;
            border: rgba(12, 22, 34, 0.2) 1px solid;
            margin-left: 10px;
            border-radius: 8px;
            margin-top: 5vh;
            .avatarBox{
                margin: 16px 0 0 16px;
                .el-icon.avatar-uploader-icon {
                    width: 78px;
                    height: 78px;
                    display: flex;
                    align-items: center;
                    justify-content: center;
                }
            }
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
                font-weight: 500;
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

        .wallet2 {
            width: 95%;
            height: 13vh;
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
                font-weight: 500;
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

        .wallet3 {
            width: 95%;
            height: 18vh;
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
                font-weight: 500;
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
