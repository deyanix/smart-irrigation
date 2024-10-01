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
          <AppBreadcrumbsEl> Zaplanowane uruchomienia </AppBreadcrumbsEl>
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
    >
      <template #body-cell-state="props">
        <q-td :props="props">
          <q-icon v-if="props.row.state" name="mdi-play" size="sm">
            <q-tooltip> Uruchomienie </q-tooltip>
          </q-icon>
          <q-icon v-else name="mdi-pause" size="sm">
            <q-tooltip> Zatrzymanie </q-tooltip>
          </q-icon>
        </q-td>
      </template>
      <template #body-cell-actions="props">
        <q-td :props="props">
          <q-btn
            icon="delete"
            round
            flat
            dense
            class="q-ml-sm"
            @click="onDelete(props.row)"
          />
        </q-td>
      </template>
    </q-table>
  </q-page>
</template>
<script setup lang="ts">
import { computed, onBeforeMount, ref } from 'vue';
import { SectionModel, SectionService } from 'src/api/Section';
import { useRoute } from 'vue-router';
import SectionSchedulesFilter from 'pages/Section/Preview/Schedules/_components/SectionSchedulesFilter.vue';
import { useSectionSchedulesColumns } from 'pages/Section/Preview/Schedules/_composables/useSectionSchedulesColumns';
import { useSearchTable } from 'src/composables/useSearchTable';
import { SectionScheduleService } from 'src/api/SectionSchedule';
import {
  SectionScheduleModel,
  SectionScheduleSearchRequest,
} from 'src/api/SectionSchedule/SectionScheduleTypes';

const columns = useSectionSchedulesColumns();
const { loading, rows, request, fetchTable, pagination } = useSearchTable<
  SectionScheduleSearchRequest,
  SectionScheduleModel
>((request) => SectionScheduleService.search(section.value!.id, request), {
  from: new Date(),
});

const $route = useRoute();
const sectionId = computed(() => parseInt($route.params.id as string));

const section = ref<SectionModel>();

async function fetchSection(): Promise<void> {
  section.value = await SectionService.getSection(sectionId.value);
}

async function onDelete(schedule: SectionScheduleModel): Promise<void> {
  await SectionScheduleService.delete(schedule.id);
  await fetchTable();
}

onBeforeMount(async () => {
  await fetchSection();
  await fetchTable();
});
</script>
