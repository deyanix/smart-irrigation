<template>
  <AppCard>
    <AppCardHeader label="Ostatnie uruchomienia" />
    <q-markup-table v-if="irrigations.length > 0">
      <tbody>
        <tr v-for="irrigation in irrigations" :key="irrigation.id">
          <td>
            <router-link
              :to="{
                name: 'SectionPreview',
                params: { id: irrigation.sectionId },
              }"
            >
              {{ irrigation.sectionName }}
            </router-link>
          </td>
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
import { FormationUtilities } from 'src/utilities/FormationUtilities';
import StatusIndicator from 'components/Status/StatusIndicator.vue';
import { useDashboardStore } from 'pages/Dashboard/_composables/useDashboardStore';

const { irrigations } = useDashboardStore();
</script>
