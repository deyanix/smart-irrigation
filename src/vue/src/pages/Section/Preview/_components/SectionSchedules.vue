<template>
  <AppCard>
    <AppCardHeader label="Harmonogram">
      <template #append>
        <q-btn icon="chevron_right" flat round dense />
      </template>
    </AppCardHeader>
    <q-markup-table v-if="schedules.length > 0">
      <tbody>
        <tr v-for="schedule in schedules" :key="schedule.id">
          <td>
            {{ FormationUtilities.formatDateFromNow(schedule.start) }}
            <span class="text-grey">
              {{ FormationUtilities.formatDateCustom(schedule.start, 'dddd') }}
            </span>
          </td>
          <td>
            {{ FormationUtilities.formatTime(schedule.start) }}
            -
            {{ FormationUtilities.formatTime(schedule.end) }}
          </td>
        </tr>
      </tbody>
    </q-markup-table>
    <q-card-section v-else class="text-center">
      Brak harmonogramu
    </q-card-section>
  </AppCard>
</template>
<script setup lang="ts">
import { useSectionPreviewStore } from 'pages/Section/Preview/_composables/useSectionPreviewStore';
import { FormationUtilities } from 'src/utilities/FormationUtilities';

const { schedules } = useSectionPreviewStore();
</script>
