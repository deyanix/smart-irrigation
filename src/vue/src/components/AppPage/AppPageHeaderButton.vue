<template>
  <q-btn :to="to" :round="$q.screen.lt.md" rounded unelevated>
    <q-icon :name="icon" :class="!$q.screen.lt.md && 'q-mr-xs'" />
    <span style="line-height: 0.5" v-show="!$q.screen.lt.md">
      <slot />
    </span>
  </q-btn>
</template>
<script setup lang="ts">
import { RouteLocationRaw } from 'vue-router';

defineProps<{
  to?: RouteLocationRaw;
  icon?: string;
}>();
</script>
