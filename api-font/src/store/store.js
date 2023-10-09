// store.js
import {createStore} from 'vuex';

export default createStore({
    state: {
        // 用户信息
        userInfo: {
            username: "",
            password: "",
            role: "",
        },
        //文档地址
        linkList: {
            //SDK文档
            SDKLink: "https://www.baidu.com",
            //开发者凭证
            voucherLink: "https://www.baidu.com",
            //在线文档
            docLink: "https://www.baidu.com",
            //用户协议
            userAgreement: "https://www.baidu.com",
        },
        //接口历史结果弹窗
        showResultDialog: false,
        //接口的细节
        callDetail: {},
        //调用接口
        call: 0,
        //tab切换
        tab: 0,
        //订单信息
        order: {
            subject: "",
            totalAmount: "",
            traceNo: "",
        },
        //search订单的条件
        search:0,
        searchParams: {

        },
        //search接口的条件
        search2:0,
        searchParams2:{

        }

    },
    mutations: {
        // 在此定义同步修改状态的方法
        updateUserInfo(state, data) {
            state.userInfo = data
        },
        showDetail(state, data) {
            state.showResultDialog = data
        },
        callDetail(state, data) {
            state.callDetail = data
        },
        call(state) {
            state.call++
        },
        changeTab(state) {
            state.tab++
        },
        setOrder(state, data) {
            state.order = data
        },
        setSearchParams(state, data) {
            state.searchParams = data
            state.search++
        },
        setSearchParams2(state, data) {
            state.searchParams2 = data
            state.search2++
        },
        setRole(state, data){
            state.userInfo.role = data
        },

    },
    actions: {
        // 在此定义异步修改状态的方法


    },
    getters: {
        // 在此定义计算属性


    }
});
