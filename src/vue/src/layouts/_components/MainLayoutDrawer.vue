<template>
  <q-drawer
    v-model="drawer"
    show-if-above
    class="main-layout__drawer q-py-md q-pr-sm"
  >
    <q-list dense padding>
      <MainLayoutDrawerItem
        icon="dashboard"
        label="Dashboard"
        :to="{ name: 'Dashboard' }"
      />
      <!-- mdi-sprinkler-variant -->
      <MainLayoutDrawerItem
        icon="mdi-pipe"
        label="Sekcje"
        :to="{ name: 'SectionList' }"
      />
      <MainLayoutDrawerItem
        icon="mdi-router-wireless"
        label="Czujniki"
        :to="{ name: 'SensorList' }"
      />
      <MainLayoutDrawerItem
        icon="settings"
        label="Ustawienia"
        :to="{ name: 'Settings' }"
      />
    </q-list>
  </q-drawer>
</template>
<script setup lang="ts">
import MainLayoutDrawerItem from './MainLayoutDrawerItem.vue';

const drawer = defineModel<boolean>('drawer');
</script>
<style lang="scss">
.main-layout__drawer {
  background: $grey-2;
}
</style>
