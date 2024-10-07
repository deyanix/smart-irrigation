<template>
  <q-page padding>
    <AppPageHeader>
      <template #breadcrumbs>
        <AppBreadcrumbs class="col">
          <AppBreadcrumbsEl>Sekcje</AppBreadcrumbsEl>
        </AppBreadcrumbs>
      </template>
    </AppPageHeader>

    <q-table :columns="columns" :rows="rows" flat bordered>
      <template #body-cell-status="props">
        <q-td :props="props">
          <StatusIndicator :active="props.row.irrigating" />
        </q-td>
      </template>
      <template #body-cell-actions="props">
        <q-td :props="props">
          <SectionController :section="props.row" />
          <q-btn
            flat
            round
            dense
            icon="chevron_right"
            :to="{ name: 'SectionPreview', params: { id: props.row.id } }"
          />
        </q-td>
      </template>
    </q-table>
  </q-page>
</template>
<script setup lang="ts">
import { useSectionColumns } from 'pages/Section/List/_composables/useSectionColumns';
import StatusIndicator from 'components/Status/StatusIndicator.vue';
import { SectionModel, SectionService } from 'src/api/Section';
import { onBeforeMount, ref } from 'vue';
import { useInstallationId } from 'src/composables/useInstallationId';
import SectionController from 'pages/Section/_components/SectionController.vue';

const installationId = useInstallationId();

const columns = useSectionColumns();
const rows = ref<SectionModel[]>([]);

async function fetchTable() {
  rows.value = await SectionService.getSections(installationId.value);
}

onBeforeMount(() => fetchTable());
</script>
