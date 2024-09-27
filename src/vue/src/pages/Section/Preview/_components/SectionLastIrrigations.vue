<template>
  <AppCard>
    <AppCardHeader label="Ostatnie uruchomienia">
      <template #append>
        <q-btn icon="chevron_right" flat round dense />
      </template>
    </AppCardHeader>
    <q-markup-table v-if="lastIrrigations.length > 0">
      <tbody>
        <tr v-for="(irrigation, index) in lastIrrigations" :key="index">
          <td>
            {{ FormationUtilities.formatDateFromNow(irrigation.start) }}
            <span class="text-grey">
              {{
                FormationUtilities.formatDateCustom(irrigation.start, 'dddd')
              }}
            </span>
          </td>
          <td>
            <template v-if="irrigation.finished">
              {{ FormationUtilities.formatTime(irrigation.start) }}
              -
              {{ FormationUtilities.formatTime(irrigation.end) }}
            </template>
            <template v-else> trwa </template>
          </td>
          <td>
            <StatusIndicator v-if="!irrigation.finished" active />
          </td>
        </tr>
      </tbody>
    </q-markup-table>
    <q-card-section v-else class="text-center">
      Brak przeszłych uruchomień
    </q-card-section>
  </AppCard>
</template>
<script setup lang="ts">
import { useSectionPreviewStore } from 'pages/Section/Preview/_composables/useSectionPreviewStore';
import { computed } from 'vue';
import { FormationUtilities } from 'src/utilities/FormationUtilities';
import StatusIndicator from 'components/Status/StatusIndicator.vue';

const { irrigations } = useSectionPreviewStore();

const lastIrrigations = computed(() => irrigations.value.slice(0, 3));
</script>
