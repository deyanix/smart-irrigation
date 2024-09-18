<template>
  <q-layout view="hHh Lpr lFf">
    <MainLayoutHeader v-model:drawer="drawer" />
    <MainLayoutDrawer v-model:drawer="drawer" />
    <MainLayoutContainer />
  </q-layout>
</template>
<script setup lang="ts">
import { ref } from 'vue';
import MainLayoutDrawer from './_components/MainLayoutDrawer.vue';
import MainLayoutHeader from './_components/MainLayoutHeader.vue';
import MainLayoutContainer from 'layouts/_components/MainLayoutContainer.vue';

const drawer = ref(true);
</script>
