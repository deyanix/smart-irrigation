<template>
  <q-btn flat round icon="more_vert">
    <q-menu self="top right" anchor="bottom right">
      <q-list>
        <q-item
          clickable
          :to="{ name: 'SectionSchedules', params: { id: section?.id } }"
        >
          <q-item-section> Zaplanowane nawodnienia </q-item-section>
        </q-item>
      </q-list>
    </q-menu>
  </q-btn>
</template>
<script setup lang="ts">
import { useSectionPreviewStore } from 'pages/Section/Preview/_composables/useSectionPreviewStore';

const { section } = useSectionPreviewStore();
</script>
