<template>
  <AppCard>
    <AppCardHeader label="Najbliższe uruchomienia">
      <template #append>
        <q-btn icon="chevron_right" flat round dense />
      </template>
    </AppCardHeader>
    <q-markup-table v-if="irrigations.length > 0">
      <tbody>
        <tr v-for="(irrigation, index) in irrigations" :key="index">
          <td>
            {{ FormationUtilities.formatDateFromNow(irrigation.start) }}
          </td>
          <td>
            {{ FormationUtilities.formatTime(irrigation.start) }}
            -
            {{ FormationUtilities.formatTime(irrigation.end) }}
          </td>
        </tr>
      </tbody>
    </q-markup-table>
  </AppCard>
</template>
<script setup lang="ts">
import { onBeforeMount, ref } from 'vue';
import { SectionIrrigationModel } from 'src/api/SectionIrrigation/SectionIrrigationTypes';
import { SectionIrrigationService } from 'src/api/SectionIrrigation/SectionIrrigationService';
import { FormationUtilities } from 'src/utilities/FormationUtilities';

const props = defineProps<{
  sectionId: number;
}>();

const irrigations = ref<SectionIrrigationModel[]>([]);

onBeforeMount(async () => {
  const data = await SectionIrrigationService.getUpcomingIrrigations(
    props.sectionId
  );
  irrigations.value = data.slice(0, 5);
});
</script>
