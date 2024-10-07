<template>
  <q-page padding>
    <AppPageHeader>
      <template #breadcrumbs>
        <AppBreadcrumbs class="col">
          <AppBreadcrumbsEl>Czujniki</AppBreadcrumbsEl>
        </AppBreadcrumbs>
      </template>
    </AppPageHeader>

    <q-table :columns="columns" :rows="rows" flat bordered>
      <template #body-cell-actions="props">
        <q-td :props="props">
          <q-btn
            flat
            round
            dense
            icon="chevron_right"
            :to="{ name: 'SensorPreview', params: { id: props.row.id } }"
          />
        </q-td>
      </template>
    </q-table>
  </q-page>
</template>
<script setup lang="ts">
import { useInstallationId } from 'src/composables/useInstallationId';
import { onBeforeMount, ref } from 'vue';
import { useSensorColumns } from 'pages/Sensor/List/_composables/useSensorColumns';
import { SensorModel, SensorService } from 'src/api/Sensor';

const installationId = useInstallationId();

const columns = useSensorColumns();
const rows = ref<SensorModel[]>([]);

async function fetchTable() {
  rows.value = await SensorService.getSensors(installationId.value);
}

onBeforeMount(() => fetchTable());
</script>
