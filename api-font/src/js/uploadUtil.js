import http from "@/js/http";

const uploadUtil = {
    upload: async (fileName) => {
        try {
            const res = await http.get("/interfaceInfo/getFileCheck");
            return `/api/interfaceInfo/getFile?fileName=${fileName}&token=${res.data.data}`;
        } catch (error) {
            // 处理错误
            console.error(error);
            throw error;
        }
    }
}

export default uploadUtil;
