<template>
  <span
    class="status-indicator"
    :class="{ 'status-indicator--active': active }"
  />
</template>
<script setup lang="ts">
defineProps<{
  active: boolean;
}>();
</script>
<style lang="scss">
.status-indicator {
  width: 12px;
  height: 12px;
  display: inline-block;
  border-radius: 50%;
  background: $negative;

  &--active {
    background: $positive;
    animation: blinker 1s linear infinite;
  }
}

@keyframes blinker {
  20% {
    opacity: 0;
  }
  50% {
    opacity: 1;
  }
  90% {
    opacity: 1;
  }
}
</style>
