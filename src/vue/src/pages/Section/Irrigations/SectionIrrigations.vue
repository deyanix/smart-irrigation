<template>
  <q-page padding>
    <AppPageHeader>
      <template #breadcrumbs>
        <AppBreadcrumbs class="col">
          <AppBreadcrumbsEl :to="{ name: 'SectionList' }">
            Sekcje
          </AppBreadcrumbsEl>
          <AppBreadcrumbsEl
            :to="{ name: 'SectionPreview', params: { id: section?.id } }"
            :loading="!section"
          >
            {{ section?.name }}
          </AppBreadcrumbsEl>
          <AppBreadcrumbsEl> Uruchomienia </AppBreadcrumbsEl>
        </AppBreadcrumbs>
      </template>
    </AppPageHeader>
    <SectionSchedulesFilter
      v-model="request"
      class="q-mb-md"
      @search="fetchTable"
    />
    <q-table
      v-model:pagination="pagination"
      :rows="rows"
      :columns="columns"
      :loading="loading"
      @request="fetchTable"
      flat
      bordered
    />
  </q-page>
</template>
<script setup lang="ts">
import { computed, onBeforeMount, ref } from 'vue';
import { SectionModel, SectionService } from 'src/api/Section';
import { useRoute } from 'vue-router';
import SectionSchedulesFilter from 'pages/Section/Schedules/_components/SectionSchedulesFilter.vue';
import { useSearchTable } from 'src/composables/useSearchTable';
import {
  IrrigationModel,
  IrrigationSearchRequest,
} from 'src/api/SectionIrrigation/SectionIrrigationTypes';
import { SectionIrrigationService } from 'src/api/SectionIrrigation/SectionIrrigationService';
import { useSectionIrrigationsColumns } from 'pages/Section/Irrigations/_composables/useSectionIrrigationsColumns';

const columns = useSectionIrrigationsColumns();
const { loading, rows, request, fetchTable, pagination } = useSearchTable<
  IrrigationSearchRequest,
  IrrigationModel
>((request) => SectionIrrigationService.search(section.value!.id, request), {});

const $route = useRoute();
const sectionId = computed(() => parseInt($route.params.id as string));

const section = ref<SectionModel>();

async function fetchSection(): Promise<void> {
  section.value = await SectionService.getSection(sectionId.value);
}

onBeforeMount(async () => {
  await fetchSection();
  await fetchTable();
});
</script>
