import { createApp } from 'vue';
import { createBootstrap } from 'bootstrap-vue-next';

import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap-vue-next/dist/bootstrap-vue-next.css';

import App from './App.vue';

const app = createApp(App);
const bootstrapVue = createBootstrap();

app.use(bootstrapVue);
app.mount('#app');
