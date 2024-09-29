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
        <SectionController :section="section" @change="fetchStore" />
        <SectionActions />
      </template>
    </AppPageHeader>
    <div class="row q-col-gutter-md">
      <div class="col-12 col-md-6">
        <SectionNearestIrrigations />
      </div>
      <div class="col-12 col-md-6">
        <SectionLastIrrigations />
      </div>
      <div class="col-12">
        <SectionSlots />
      </div>
    </div>
  </q-page>
</template>
<script setup lang="ts">
import { useRoute } from 'vue-router';
import { computed } from 'vue';
import SectionSlots from './_components/SectionSlots.vue';
import SectionNearestIrrigations from 'pages/Section/Preview/_components/SectionNearestIrrigations.vue';
import { defineSectionPreviewStore } from 'pages/Section/Preview/_composables/useSectionPreviewStore';
import SectionLastIrrigations from 'pages/Section/Preview/_components/SectionLastIrrigations.vue';
import SectionController from 'pages/Section/_components/SectionController.vue';
import SectionActions from 'pages/Section/Preview/_components/SectionActions.vue';

const $route = useRoute();

const sectionId = computed(() => parseInt($route.params.id as string));
const { section, fetchStore } = defineSectionPreviewStore(sectionId);
</script>
