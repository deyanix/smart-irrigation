<template>
  <q-item
    :to="to"
    clickable
    exact
    v-ripple
    active-class="menu-layout__drawer-item--active"
    class="menu-layout__drawer-item"
  >
    <q-item-section avatar>
      <q-avatar :icon="icon" />
    </q-item-section>
    <q-item-section>
      {{ label }}
    </q-item-section>
  </q-item>
</template>
<script setup lang="ts">
import { RouteLocationRaw } from 'vue-router';

defineProps<{ icon?: string; label?: string; to?: RouteLocationRaw }>();
</script>
<style lang="scss">
.menu-layout__drawer-item {
  padding-left: 16px;
  font-weight: 500;
  border-radius: 0 24px 24px 0;

  &--active {
    color: $primary;
    background: transparentize($primary, 0.9);
  }
}
</style>
