<template>
  <q-item dense class="app-card-header">
    <q-item-section v-if="$slots['prepend']" avatar>
      <slot name="prepend" />
    </q-item-section>
    <q-item-section class="text-subtitle2">
      {{ label }}
    </q-item-section>
    <q-item-section v-if="$slots['append']" avatar>
      <slot name="append" />
    </q-item-section>
  </q-item>
  <q-separator />
</template>
<script setup lang="ts">
defineProps<{ label?: string }>();
</script>
<style lang="scss">
.app-card-header {
  min-height: 38px;
}
</style>
