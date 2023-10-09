<template>
  <router-view>
  </router-view>
</template>

<script setup>
import {getTheme} from "@/theme/theme";
import {onBeforeMount} from "vue";


onBeforeMount(() => {
  getTheme && getTheme('themeColor');
})
</script>

<style>
body {
  width: 100vw;
  height: 100vh;
  overflow: hidden;
  padding: 0;
  margin: 0;
  background: rgba(239, 223, 223, 0.26);
}

* {
  padding: 0;
  left: 0;
}

::-webkit-scrollbar {
  display: none;
}

.v-md-copy-code-btn {
  margin-left: 100% !important;
  transform: translateX(-150%);
}

</style>
