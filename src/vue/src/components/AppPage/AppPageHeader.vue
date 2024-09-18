<template>
  <div class="app-page-header row justify-between items-center q-mb-lg">
    <div class="col">
      <slot name="breadcrumbs" />
    </div>
    <div v-if="$slots['actions']" class="col-auto">
      <div class="row items-center q-gutter-sm">
        <slot name="actions" />
      </div>
    </div>
  </div>
</template>
<style lang="scss">
.app-page-header {
  min-height: 42px;
}
</style>
<script setup lang="ts"></script>
