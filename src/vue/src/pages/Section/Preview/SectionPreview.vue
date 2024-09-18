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
      <div class="col-12">
        <AppCard>
          <div class="row q-col-gutter-md">
            <div class="col-12 col-md-3">
              <q-item>
                <q-item-section>
                  <q-item-label caption> Stan </q-item-label>
                  <q-item-label>Wyłączony</q-item-label>
                </q-item-section>
              </q-item>
            </div>
          </div>
        </AppCard>
      </div>
      <div class="col-12 col-md-6">
        <AppCard>
          <AppCardHeader label="Najbliższe uruchomienia">
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
      <div class="col-12 col-md-6">
        <AppCard>
          <AppCardHeader label="Harmonogram">
            <template #append>
              <q-btn icon="add" flat round dense />
            </template>
          </AppCardHeader>
          <q-markup-table>
            <tbody>
              <tr>
                <td>codziennie</td>
                <td>9:15 - 9:30</td>
                <td class="text-right">
                  <q-btn icon="edit" flat round dense />
                </td>
              </tr>
              <tr>
                <td>pn, wt, śr, sb, nd</td>
                <td>9:15 - 9:30</td>
                <td class="text-right">
                  <q-btn icon="edit" flat round dense />
                </td>
              </tr>
            </tbody>
          </q-markup-table>
        </AppCard>
      </div>
    </div>
  </q-page>
</template>
<script setup lang="ts">
import { Dialog } from 'quasar';
import SectionStartDialog from '../_dialogs/SectionStartDialog.vue';

function onStop() {
  Dialog.create({
    component: SectionStartDialog,
  });
}

function onStart() {
  Dialog.create({
    component: SectionStartDialog,
  });
}
</script>
