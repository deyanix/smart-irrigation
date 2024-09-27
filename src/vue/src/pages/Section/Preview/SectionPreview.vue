<template>
  <q-page padding>
    <AppPageHeader>
      <template #breadcrumbs>
        <AppBreadcrumbs class="col">
          <AppBreadcrumbsEl :to="{ name: 'SectionList' }">
            Sekcje
          </AppBreadcrumbsEl>
          <AppBreadcrumbsEl :loading="!section">
            {{ section?.name }}
          </AppBreadcrumbsEl>
        </AppBreadcrumbs>
      </template>
      <template #actions>
        <q-btn flat round icon="mdi-pause" @click="onStop" />
        <q-btn flat round icon="mdi-play" @click="onStart" />
        <q-btn flat round icon="more_vert" />
      </template>
    </AppPageHeader>
    <div class="row q-col-gutter-md">
      <div class="col-12 col-md-6">
        <SectionNearestIrrigations />
      </div>
      <div class="col-12 col-md-6">
        <SectionLastIrrigations />
      </div>
      <div class="col-12 col-md-6">
        <SectionSlots />
      </div>
      <div class="col-12 col-md-6">
        <SectionSchedules />
      </div>
    </div>
  </q-page>
</template>
<script setup lang="ts">
import { Dialog } from 'quasar';
import SectionStartDialog from '../_dialogs/SectionStartDialog.vue';
import { useRoute } from 'vue-router';
import { computed } from 'vue';
import SectionSlots from './_components/SectionSlots.vue';
import SectionNearestIrrigations from 'pages/Section/Preview/_components/SectionNearestIrrigations.vue';
import { defineSectionPreviewStore } from 'pages/Section/Preview/_composables/useSectionPreviewStore';
import SectionLastIrrigations from 'pages/Section/Preview/_components/SectionLastIrrigations.vue';
import SectionSchedules from 'pages/Section/Preview/_components/SectionSchedules.vue';

const $route = useRoute();

const sectionId = computed(() => parseInt($route.params.id as string));
const { section, fetchStore } = defineSectionPreviewStore(sectionId);

function onStop() {
  Dialog.create({
    component: SectionStartDialog,
  }).onOk(() => fetchStore());
}

function onStart() {
  Dialog.create({
    component: SectionStartDialog,
    componentProps: {
      sectionId: sectionId.value,
    },
  }).onOk(() => fetchStore());
}
</script>
