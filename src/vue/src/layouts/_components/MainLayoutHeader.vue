<template>
  <q-header class="main-layout__header bg-white text-black" bordered>
    <q-toolbar>
      <q-btn
        flat
        dense
        round
        icon="menu"
        aria-label="Menu"
        @click="drawer = !drawer"
      />

      <q-toolbar-title class="flex items-center">
        <img src="~/assets/logo.png" alt="Logo" />
        <q-separator vertical class="q-mx-md" />
        <q-select
          v-model="installationStore.currentInstallation"
          borderless
          dense
          :options="installationStore.installations"
          option-value="id"
          option-label="name"
          map-options
        />
      </q-toolbar-title>
    </q-toolbar>
  </q-header>
</template>
<script setup lang="ts">
import { useInstallationStore } from 'stores/installation';

const installationStore = useInstallationStore();

const drawer = defineModel<boolean>('drawer');
</script>
<style lang="scss">
.main-layout__header {
  .q-toolbar {
    min-height: 60px;
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
