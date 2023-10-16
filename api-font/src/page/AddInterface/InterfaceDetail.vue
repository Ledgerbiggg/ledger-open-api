<template>
  <div class="interface">
    <el-form
        :rules="rules"
        :model="interfaceAdd"
        label-width="120px"
    >
      <el-form-item label="名称" prop="name">
        <el-input v-model="interfaceAdd.name"/>
      </el-form-item>
      <el-form-item label="url" prop="url">
        <el-input v-model="interfaceAdd.url"/>
      </el-form-item>
      <el-form-item label="描述" prop="description">
        <el-input v-model="interfaceAdd.description"/>
      </el-form-item>
      <el-form-item label="消费">
        <el-input-number v-model="interfaceAdd.consume" :min="1" :max="10"/>
      </el-form-item>
      <el-row>
        <el-col :span="8">
          <el-form-item label="状态">
            <el-select v-model="interfaceAdd.status" placeholder="请选择">
              <el-option label="启用" :value="1"/>
              <el-option label="关闭" :value="0"/>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="方法">
            <el-select v-model="interfaceAdd.method" placeholder="请选择">
              <el-option label="GET" value="GET"/>
              <el-option label="POST" value="POST"/>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="响应类型">
            <el-select v-model="interfaceAdd.resp_type" placeholder="请选择">
              <el-option label="JSON" value="JSON"/>
              <el-option label="IMAGE" value="IMAGE"/>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item label="响应示例值" prop="example">
        <el-input v-model="interfaceAdd.example" type="textarea"/>
      </el-form-item>
      <el-form-item label="接口图片上传" prop="img_url">
        <FileUpload :getUrl="getUrl" />
<!--        <el-input v-model="interfaceAdd.img_url"/>-->
      </el-form-item>
    </el-form>
    <div class="next">
      <el-form-item>
        <el-button type="primary" @click="next(1)" style="margin-left: auto;">>></el-button>
      </el-form-item>
    </div>
  </div>
</template>

<script setup>
import {onMounted, reactive, ref, watch} from 'vue'
import store from "@/store/store";
import {ElMessage} from "element-plus";
import FileUpload from "@/components/FileCompoment/FileUpload.vue";

// do not use same name with ref
const interfaceAdd = ref({
  name: '',
  status: 1,
  consume: 1,
  method: 'GET',
  description: '',
  url: '',
  img_url: '',
  resp_type: 'JSON',
  example: ''
})


const rules = reactive({
  name: [
    {required: true, message: '输入接口名称', trigger: 'blur'},
    {min: 3, max: 12, message: '长度3-12', trigger: 'blur'},
  ],
  url: [
    {required: true, message: '输入接口url', trigger: 'blur'},
    {min: 3, message: '长度不小于3', trigger: 'blur'},
  ],
  description: [
    {required: true, message: '输入接口描述', trigger: 'blur'},
    {min: 3, message: '长度不小于3', trigger: 'blur'},
  ],
  img_url: [
    {required: true, message: '输入接口图片地址', trigger: 'blur'},
  ],
  example: [
    {required: true, message: '输入接口示例值', trigger: 'blur'},
  ],

})
onMounted(() => {
  interfaceAdd.value = store.state.interfaceAdd
})

watch(interfaceAdd, (newVal) => {
  store.commit('setInterfaceAdd', newVal);
});

const getUrl = (url) => {
  interfaceAdd.value.img_url = url
}


const next = (int) => {
  if (interfaceAdd.value.name === ''
      || interfaceAdd.value.url === ''
      || interfaceAdd.value.description === ''
      || interfaceAdd.value.img_url === ''
      || interfaceAdd.value.example === '') {
    ElMessage.warning('请将信息填写完整');
    return
  }
  store.commit('setActive', int);
}

</script>


<style>
.interface .el-input-number__increase {
  margin-left: 100%;
  transform: translateX(-100%);
}
</style>
<style scoped>
.interface {
  position: relative;
  box-sizing: border-box;
  padding: 35px;
  width: 90%;
  margin: 0 auto;
  background: #ffffff;
  height: 75vh;
  border-radius: 8px;
  .next {
    margin-top: 3vh;
    position: absolute;
    left: 100%;
    transform: translateX(-150%);
    bottom: 0;
  }
}
</style>