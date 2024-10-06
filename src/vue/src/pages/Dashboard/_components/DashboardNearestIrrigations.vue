<template>
  <AppCard>
    <AppCardHeader label="Nadchodzące uruchomienia" />

    <AppCardSection v-if="mainIrrigation">
      <div class="row q-gutter-sm items-end">
        <span class="text-h4">
          {{ FormationUtilities.formatTime(mainIrrigation.start) }}
          -
          {{ FormationUtilities.formatTime(mainIrrigation.end) }}
        </span>
        <span class="text-h6 text-grey">
          {{ FormationUtilities.formatDateFromNow(mainIrrigation.start) }}
        </span>
      </div>
      <router-link
        :to="{
          name: 'SectionPreview',
          params: { id: mainIrrigation.sectionId },
        }"
        class="text-subtitle2 q-mt-sm"
      >
        {{ mainIrrigation.sectionName }}
      </router-link>
    </AppCardSection>
    <AppCardSection v-else class="text-center">
      Brak zaplanowanych uruchomień
    </AppCardSection>
    <q-separator />
    <q-expansion-item v-if="otherIrrigations.length > 0" label="Więcej" dense>
      <q-markup-table flat>
        <tbody>
          <tr v-for="(irrigation, index) in otherIrrigations" :key="index">
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
    </q-expansion-item>
  </AppCard>
</template>
<script setup lang="ts">
import { FormationUtilities } from 'src/utilities/FormationUtilities';
import { computed } from 'vue';
import { useDashboardStore } from 'pages/Dashboard/_composables/useDashboardStore';

const { upcomingIrrigations } = useDashboardStore();

const mainIrrigation = computed(() => upcomingIrrigations.value.at(0));
const otherIrrigations = computed(() => upcomingIrrigations.value.slice(1, 6));
</script>
