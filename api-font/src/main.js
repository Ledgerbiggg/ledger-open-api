import { createApp } from 'vue';
import App from '@/App.vue';
import ElementPlus from 'element-plus';
import store from '@/store/store';
import router from '@/router/router';
import DialogCenter from "@/components/dialog/DialogCenter.vue";
import DialogDraggable from "@/components/dialog/DialogDraggable.vue";
import FormNested from "@/components/dialog/FormNested.vue";
import RequestParametersEdit from "@/components/dialog/RequestParametersEdit.vue";

import 'element-plus/dist/index.css';
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

const app = createApp(App)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
import VMdPreview from '@kangc/v-md-editor/lib/preview';
import '@kangc/v-md-editor/lib/style/preview.css';
import githubTheme from '@kangc/v-md-editor/lib/theme/github.js';
import '@kangc/v-md-editor/lib/theme/style/github.css';
import createCopyCodePlugin from '@kangc/v-md-editor/lib/plugins/copy-code/index';
import '@kangc/v-md-editor/lib/plugins/copy-code/copy-code.css';


// highlightjs
import hljs from 'highlight.js';

VMdPreview.use(githubTheme, {
    Hljs: hljs,
});
VMdPreview.use(createCopyCodePlugin());

app.use(VMdPreview);
app.component("FormNested", FormNested);
app.component("DialogCenter", DialogCenter);
app.component("DialogDraggable", DialogDraggable);
app.component("RequestParametersEdit", RequestParametersEdit);
app.use(ElementPlus);
app.use(store);
app.use(router);

app.mount('#app');



