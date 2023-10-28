// tokenUtil.js
export default  {
    getToken: () =>{
        return localStorage.getItem('token').replace("Bearer ",""); // 从 localStorage 中获取 token
    }
};
