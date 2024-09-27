<template>
  <q-breadcrumbs-el v-bind="$props" class="app-breadcrumbs-el">
    <q-spinner v-if="loading" />
    <slot v-else />
  </q-breadcrumbs-el>
</template>
<script setup lang="ts">
import { QBreadcrumbsElProps } from 'quasar';

defineOptions({
  name: 'QBreadcrumbsEl',
});
defineProps<QBreadcrumbsElProps & { loading?: boolean }>();
</script>
<style lang="scss">
.app-breadcrumbs-el {
  border-radius: 8px;
  padding: 4px;

  @at-root a#{&}:hover {
    background: $grey-3 !important;
  }

  > span {
    max-width: 240px;
    text-overflow: ellipsis;
    white-space: nowrap;
    overflow: hidden;
  }
}
</style>
