<template>
  <q-layout view="hHh Lpr lFf">
    <q-header class="main-layout__header bg-white text-black" bordered>
      <q-toolbar>
        <q-btn
          flat
          dense
          round
          icon="menu"
          aria-label="Menu"
          @click="toggleLeftDrawer"
        />

        <q-toolbar-title class="flex items-center">
          <img src="~/assets/logo.png" alt="Logo"/>
          <q-separator vertical class="q-mx-md"/>
          <q-select
            model-value="Instalacja 1"
            borderless
            dense
            :options="['Instalacja 1', 'Instalacja 2']"
          />
        </q-toolbar-title>
      </q-toolbar>
    </q-header>

    <q-drawer
      v-model="leftDrawerOpen"
      show-if-above
      class="bg-grey-2"
    />

    <q-page-container class="bg-grey-2">
      <router-view />
    </q-page-container>
  </q-layout>
</template>
<script setup lang="ts">
import { ref } from 'vue';

const leftDrawerOpen = ref(false);

function toggleLeftDrawer () {
  leftDrawerOpen.value = !leftDrawerOpen.value;
}
</script>
<style lang="scss">
.main-layout__header {
  .q-toolbar {
    min-height: 64px;
  }

  .q-select {
    .q-field__native {
      font-size: 1.2rem;
      font-weight: 500;
    }
  }

  img {
    max-height: 32px;
  }
}
</style>
