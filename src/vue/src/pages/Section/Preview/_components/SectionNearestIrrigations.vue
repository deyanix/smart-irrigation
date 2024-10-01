<template>
  <AppCard>
    <AppCardHeader label="Nadchodzące uruchomienia">
      <template #append>
        <q-btn
          icon="chevron_right"
          flat
          round
          dense
          :to="{
            name: 'SectionUpcomingIrrigations',
            params: { id: section?.id },
          }"
        />
      </template>
    </AppCardHeader>
    <q-markup-table v-if="nearestIrrigations.length > 0">
      <tbody>
        <tr v-for="(irrigation, index) in nearestIrrigations" :key="index">
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
import { useSectionPreviewStore } from 'pages/Section/Preview/_composables/useSectionPreviewStore';
import { computed } from 'vue';

const { section, upcomingIrrigations } = useSectionPreviewStore();

const nearestIrrigations = computed(() =>
  upcomingIrrigations.value.slice(0, 3)
);
</script>
