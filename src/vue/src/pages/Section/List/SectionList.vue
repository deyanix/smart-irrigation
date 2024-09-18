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
  </q-page>
</template>
<script setup lang="ts">
import { useSectionColumns } from 'pages/Section/List/_composables/useSectionColumns';

const columns = useSectionColumns();
const rows = [
  { name: 'Sekcja 1', description: 'Obok altanki', active: true },
  { name: 'Sekcja 2', description: 'Obok altanki', active: false },
  { name: 'Sekcja 3', description: 'Obok altanki', active: false },
];
</script>
