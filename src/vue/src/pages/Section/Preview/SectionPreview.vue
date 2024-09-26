<template>
  <q-page padding>
    <AppPageHeader>
      <template #breadcrumbs>
        <AppBreadcrumbs class="col">
          <AppBreadcrumbsEl :to="{ name: 'SectionList' }">
            Sekcje
          </AppBreadcrumbsEl>
          <AppBreadcrumbsEl> Sekcja 1 </AppBreadcrumbsEl>
        </AppBreadcrumbs>
      </template>
      <template #actions>
        <q-btn flat round icon="mdi-pause" @click="onStop" />
        <q-btn flat round icon="mdi-play" @click="onStart" />
        <q-btn flat round icon="more_vert" />
      </template>
    </AppPageHeader>
    <div class="row q-col-gutter-md">
      <div class="col-12 col-md-6">
        <SectionNearestIrrigations :section-id="sectionId" />
      </div>
      <div class="col-12 col-md-6">
        <AppCard>
          <AppCardHeader label="Ostatnie uruchomienia">
            <template #append>
              <q-btn icon="chevron_right" flat round dense />
            </template>
          </AppCardHeader>
          <q-markup-table>
            <tbody>
              <tr>
                <td>2024-09-18 12:11</td>
                <td>12 minut</td>
                <td>Automatycznie</td>
              </tr>
              <tr>
                <td>2024-09-18 12:11</td>
                <td>12 minut</td>
                <td>Ręcznie</td>
              </tr>
            </tbody>
          </q-markup-table>
        </AppCard>
      </div>
      <!-- Karta "Harmonogram" oraz "Zaplanowane" ("uruchomienie"/"wyłączenie") -->
      <div class="col-12 col-md-6">
        <SectionSlots :section-id="sectionId" />
      </div>
    </div>
  </q-page>
</template>
<script setup lang="ts">
import { Dialog } from 'quasar';
import SectionStartDialog from '../_dialogs/SectionStartDialog.vue';
import { useRoute } from 'vue-router';
import { computed } from 'vue';
import SectionSlots from './_components/SectionSlots.vue';
import SectionNearestIrrigations from 'pages/Section/Preview/_components/SectionNearestIrrigations.vue';

const $route = useRoute();

const sectionId = computed(() => parseInt($route.params.id as string));

function onStop() {
  Dialog.create({
    component: SectionStartDialog,
  });
}

function onStart() {
  Dialog.create({
    component: SectionStartDialog,
    componentProps: {
      sectionId: sectionId.value,
    },
  });
}
</script>
