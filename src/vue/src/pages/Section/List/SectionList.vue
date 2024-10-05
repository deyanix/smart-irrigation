<template>
  <q-page padding>
    <AppPageHeader>
      <template #breadcrumbs>
        <AppBreadcrumbs class="col">
          <AppBreadcrumbsEl>Sekcje</AppBreadcrumbsEl>
        </AppBreadcrumbs>
      </template>

      <template #actions>
        <q-btn round flat icon="more_vert" />
        <!--        <AppPageHeaderButton-->
        <!--          icon="add"-->
        <!--          color="primary"-->
        <!--          :to="{ name: 'SectionCreate' }"-->
        <!--        >-->
        <!--          Nowa sekcja-->
        <!--        </AppPageHeaderButton>-->
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
          <q-btn
            flat
            round
            dense
            icon="mdi-pause"
            class="q-mr-sm"
            @click="onStop(props.row)"
          />
          <q-btn
            flat
            round
            dense
            icon="mdi-play"
            class="q-mr-sm"
            @click="onStart(props.row)"
          />
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
import { Dialog } from 'quasar';
import SectionPauseDialog from 'pages/Section/_dialogs/SectionPauseDialog.vue';
import SectionStartDialog from 'pages/Section/_dialogs/SectionStartDialog.vue';

const installationId = useInstallationId();

const columns = useSectionColumns();
const rows = ref<SectionModel[]>([]);

async function fetchTable() {
  rows.value = await SectionService.getSections(installationId.value);
}

function onStop(section: SectionModel) {
  Dialog.create({
    component: SectionPauseDialog,
    componentProps: {
      section,
    },
  }).onOk(() => fetchTable());
}

function onStart(section: SectionModel) {
  Dialog.create({
    component: SectionStartDialog,
    componentProps: {
      section,
    },
  }).onOk(() => fetchTable());
}

onBeforeMount(async () => {
  await fetchTable();
});
</script>
