<template>
  <q-breadcrumbs v-bind="$props" class="app-breadcrumbs">
    <template #separator>
      <q-icon name="chevron_right" size="24px" />
    </template>
    <slot />
  </q-breadcrumbs>
</template>
<script setup lang="ts">
import { QBreadcrumbsProps } from 'quasar';

defineOptions({ name: 'QBreacrumbs' });
defineProps<QBreadcrumbsProps>();
</script>
<style lang="scss">
.app-breadcrumbs {
  font-size: 20px;
  font-weight: 500;
  margin: -4px;

  .q-breadcrumbs__separator {
    display: flex;
    align-items: center;

    & > .q-icon {
      width: 12px;
    }
  }
}
</style>
