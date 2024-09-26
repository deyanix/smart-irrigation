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
        <AppPageHeaderButton
          icon="add"
          color="primary"
          :to="{ name: 'SectionCreate' }"
        >
          Nowa sekcja
        </AppPageHeaderButton>
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
            :icon="props.row.active ? 'mdi-pause' : 'mdi-play'"
            class="q-mr-sm"
          />
          <q-btn
            flat
            round
            dense
            icon="chevron_right"
            :to="{ name: 'SectionPreview', params: { id: 1 } }"
          />
        </q-td>
      </template>
    </q-table>

    <div class="row q-gutter-sm">
      <q-btn
        v-for="weekday in ['pn', 'wt', 'śr', 'cz', 'pt', 'so', 'nd']"
        :key="weekday"
        :label="weekday"
        unelevated
        round
        :color="'primary'"
      />
    </div>
  </q-page>
</template>
<script setup lang="ts">
import { useSectionColumns } from 'pages/Section/List/_composables/useSectionColumns';
import StatusIndicator from 'components/Status/StatusIndicator.vue';
import { SectionModel, useSectionService } from 'src/api/Section';
import { onBeforeMount, ref } from 'vue';

const { getSections } = useSectionService();

const columns = useSectionColumns();
const rows = ref<SectionModel[]>([]);

async function fetchTable() {
  rows.value = await getSections();
}

onBeforeMount(async () => {
  await fetchTable();
});
</script>
