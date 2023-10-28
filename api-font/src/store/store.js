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
            //上传文件地址
            uploadAvatar: "http://60.204.241.30/api/user/uploadAvatar",
            //上传文件地址
            uploadFile: "http://60.204.241.30/api/interfaceInfo/uploadFile",
            //上传文件地址
            wsLink: process.env.VUE_APP_API_WS_URL,
        },
        //接口历史结果弹窗
        showResultDialog: false,
        //请求参数弹窗
        requestParametersEditDialog: false,
        //响应参数弹窗
        responseParametersEditDialog: false,
        //接口的细节弹窗
        InterfaceEditDialog: false,
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
        search: 0,
        searchParams: {},
        //search接口的条件
        search2: 0,
        searchParams2: {},
        //订单详情
        orderDetail: {},
        //请求参数详情
        requestParameterDetails: {},
        //响应参数详情
        responseParameterDetails: {},
        InterfaceDetails: {},
        //导航
        active: 1,
        //添加接口
        interfaceAdd: {
            name: '',
            status: 1,
            consume: 1,
            method: 'GET',
            description: '',
            url: '',
            img_url: '',
            resp_type: 'JSON',
            example: '',
            need_certificate:0
        },
        //添加请求参数
        requestParameters:[],
        //添加响应参数
        responseParameters:[],

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
        setRole(state, data) {
            state.userInfo.role = data
        },
        setOrderDetail(state, data) {
            state.orderDetail = data
        },
        setInterfaceDetail(state, data) {
            state.interfaceDetail = data
        },
        showRequestParametersEditDialog(state, data) {
            state.requestParametersEditDialog = data
        },
        setRequestParameterDetails(state, data) {
            state.requestParameterDetails = data
        },
        showResponseParametersEditDialog(state, data) {
            state.responseParametersEditDialog = data
        },
        setResponseParameterDetails(state, data) {
            state.responseParameterDetails = data
        },
        showInterfaceEditDialog(state, data) {
            state.InterfaceEditDialog = data
        },
        setInterfaceDetails(state, data) {
            state.InterfaceDetails = data
        },
        setActive(state, data) {
            state.active += data
        },
        setInterfaceAdd(state, data) {
            state.interfaceAdd = data
        },
        setRequestParameters(state, data) {
            state.requestParameters = data
        },
        setResponseParameters(state, data) {
            state.responseParameters = data
        },

    },
    actions: {
        // 在此定义异步修改状态的方法


    },
    getters: {
        // 在此定义计算属性


    }
});
