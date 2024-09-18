<template>
  <q-page-container class="main-layout__container">
    <div class="main-layout__page-wrapper">
      <router-view />
    </div>
  </q-page-container>
</template>
<style lang="scss">
.main-layout__container {
  background: $grey-2;
}

.main-layout__page-wrapper {
  max-width: 1200px;
}
</style>
<script setup lang="ts"></script>
