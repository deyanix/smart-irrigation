<template>
  <AppCard>
    <AppCardHeader label="Nadchodzące uruchomienia" />
    <q-markup-table v-if="nearestIrrigations.length > 0">
      <tbody>
        <tr v-for="(irrigation, index) in nearestIrrigations" :key="index">
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
            {{ FormationUtilities.formatTime(irrigation.start) }}
            -
            {{ FormationUtilities.formatTime(irrigation.end) }}
          </td>
        </tr>
      </tbody>
    </q-markup-table>
    <q-card-section v-else class="text-center">
      Brak zaplanowanych uruchomień
    </q-card-section>
  </AppCard>
</template>
<script setup lang="ts">
import { FormationUtilities } from 'src/utilities/FormationUtilities';
import { computed } from 'vue';
import { useDashboardStore } from 'pages/Dashboard/_composables/useDashboardStore';

const { upcomingIrrigations } = useDashboardStore();

const nearestIrrigations = computed(() =>
  upcomingIrrigations.value.slice(0, 5)
);
</script>
