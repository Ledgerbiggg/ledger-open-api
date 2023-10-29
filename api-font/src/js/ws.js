import tokenUtil from "@/js/tokenUtil";

const wsLink = process.env.VUE_APP_API_WS_URL
let socket;
let messageHandler;

function initWebSocket() {
    socket = new WebSocket(`${wsLink}${tokenUtil.getToken()}`);
    socket.addEventListener("open", (event) => {
        console.log(event, "WebSocket连接已打开");
    });

    socket.addEventListener("message", (event) => {
        const receivedData = event.data;
        let parse = JSON.parse(receivedData);
        if (messageHandler) {
            messageHandler(parse); // 调用订阅的消息处理函数
        }
    });

    socket.addEventListener("close", (event) => {
        if (event.wasClean) {
            console.log("WebSocket连接已关闭");
        } else {
            console.error("WebSocket连接意外关闭");
        }
    });

    socket.addEventListener("error", (event) => {
        console.error(event, "WebSocket连接出现错误");
    });
}

const sendMsg = (msg, toName) => {
    let data = {
        message: msg,
        toName: toName
    }
    socket.send(JSON.stringify(data));
}
const subscribeToMessages = (handler) => {
    messageHandler = handler;
}
export default {initWebSocket, sendMsg, subscribeToMessages};