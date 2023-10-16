<template>
  <div class="uploadFile">
    <el-upload
        class="avatar-uploader"
        :action="store.state.linkList.uploadFile"
        :show-file-list="false"
        :on-success="handleAvatarSuccess"
        :before-upload="beforeAvatarUpload"
        :headers="{ Authorization: token }"
    >
      <img v-if="imageUrl" :src="imageUrl" class="avatar" ref="avatar"/>
      <el-icon v-else class="avatar-uploader-icon">
        <Plus/>
      </el-icon>
    </el-upload>
  </div>
</template>
<script setup>
import uploadUtil from "@/js/uploadUtil";
import {ElMessage} from "element-plus";
import store from "@/store/store";
import {onMounted, ref} from "vue";
import {defineProps} from "vue";

const token = ref("")
// const imageUrl = ref('')


const props = defineProps({
  url: String,
  getUrl: Function
})
const imageUrl = ref("")

onMounted(async () => {
  if(props.url!==undefined){
    imageUrl.value = await uploadUtil.upload(props.url);
  }
})


const handleAvatarSuccess = async (response, /*uploadFile*/) => {
  imageUrl.value = await uploadUtil.upload(response.data.nameWithFileExtension);
  if(props.getUrl!==undefined){
    props.getUrl(response.data.nameWithFileExtension)
  }
  ElMessage.success("上传成功")
}

// 获取token上传
const beforeAvatarUpload = (rawFile) => {
  if (rawFile.type !== 'image/jpeg' && rawFile.type !== 'image/png' && rawFile.type !== 'image/gif') {
    ElMessage.error('请上传 jpg,png,gif 格式的图片!')
    return false
  } else if (rawFile.size / 1024 / 1024 > 2) {
    ElMessage.error('上传头像图片大小不能超过 2MB!')
    return false
  }
  token.value = window.localStorage.getItem('token');
  return true
}


</script>
<style lang="less">
.avatar {
  width: 100%;
  height: 100%;
}

.uploadFile{
  width: 128px;
  height: 128px;

  .avatar-uploader .el-upload {
    border: 1px dashed var(--el-border-color);
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    transition: var(--el-transition-duration-fast);
    width: 128px;
    height: 128px;
  }

  .avatar-uploader .el-upload:hover {
    border-color: var(--el-color-primary);
  }

  .el-icon.avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 128px;
    height: 128px;
    text-align: center;
  }

}
</style>
<style scoped>

</style>