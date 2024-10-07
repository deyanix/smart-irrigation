<template>
  <q-page padding>
    <AppPageHeader>
      <template #breadcrumbs>
        <AppBreadcrumbs class="col">
          <AppBreadcrumbsEl>Dashboard</AppBreadcrumbsEl>
        </AppBreadcrumbs>
      </template>
    </AppPageHeader>
    <div class="row q-col-gutter-md">
      <div class="col-12 col-md-6">
        <DashboardNearestIrrigations />
      </div>
      <div class="col-12 col-md-6">
        <DashboardLastIrrigations />
      </div>
      <div class="col-12">
        <DashboardMeasurements />
      </div>
    </div>
  </q-page>
</template>

<script setup lang="ts">
import { defineDashboardStore } from 'pages/Dashboard/_composables/useDashboardStore';
import DashboardNearestIrrigations from 'pages/Dashboard/_components/DashboardNearestIrrigations.vue';
import DashboardLastIrrigations from 'pages/Dashboard/_components/DashboardLastIrrigations.vue';
import DashboardMeasurements from 'pages/Dashboard/_components/DashboardMeasurements.vue';

defineDashboardStore();
</script>
