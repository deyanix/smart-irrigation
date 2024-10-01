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
          <AppBreadcrumbsEl> Nadchodzące uruchomienia </AppBreadcrumbsEl>
        </AppBreadcrumbs>
      </template>
    </AppPageHeader>
    <q-table
      :rows="rows"
      :columns="columns"
      :pagination="{ rowsPerPage: 10 }"
      flat
      bordered
    >
      <template #body-cell-startDate="props">
        <q-td :props="props">
          {{ FormationUtilities.formatDateFromNow(props.row.start) }}
          <span class="text-grey">
            {{ FormationUtilities.formatDateCustom(props.row.start, 'dddd') }}
          </span>
        </q-td>
      </template>
      <template #body-cell-actions="props">
        <q-td :props="props">
          <q-btn
            icon="mdi-information-outline"
            round
            flat
            dense
            @click="onInfo(props.row)"
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
import { IrrigationService, UpcomingIrrigationModel } from 'src/api/Irrigation';
import { useUpcomingIrrigationsColumns } from 'pages/Section/Preview/UpcomingIrrigations/_composables/useUpcomingIrrigationsColumns';
import { Dialog } from 'quasar';
import UpcomingIrrigationInfoDialog from 'pages/Section/Preview/UpcomingIrrigations/_dialogs/UpcomingIrrigationInfoDialog.vue';
import { FormationUtilities } from 'src/utilities/FormationUtilities';

const columns = useUpcomingIrrigationsColumns();

const $route = useRoute();
const sectionId = computed(() => parseInt($route.params.id as string));
const section = ref<SectionModel>();
const rows = ref<UpcomingIrrigationModel[]>([]);

async function fetch(): Promise<void> {
  section.value = await SectionService.getSection(sectionId.value);
  rows.value = await IrrigationService.getUpcomingIrrigationsBySection(
    sectionId.value
  );
}

function onInfo(irrigation: UpcomingIrrigationModel) {
  Dialog.create({
    component: UpcomingIrrigationInfoDialog,
    componentProps: {
      irrigation,
    },
  });
}

onBeforeMount(() => fetch());
</script>
