<template>
  <q-card :style="{ width }">
    <slot />
  </q-card>
</template>
<script setup lang="ts">
withDefaults(defineProps<{ width?: string }>(), {
  width: '480px',
});
</script>
