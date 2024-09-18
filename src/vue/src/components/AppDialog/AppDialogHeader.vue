<template>
  <q-card-section class="row q-pb-none">
    <div class="col">
      <div class="text-h6">{{ title }}</div>
    </div>
    <div class="col-auto">
      <q-btn icon="close" round flat v-close-popup />
    </div>
  </q-card-section>
</template>
<script setup lang="ts">
defineProps<{ title?: string }>();
</script>
