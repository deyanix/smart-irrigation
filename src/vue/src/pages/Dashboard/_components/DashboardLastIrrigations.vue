<template>
  <AppCard>
    <AppCardHeader label="Ostatnie uruchomienia" />
    <AppCardSection v-if="mainIrrigation">
      <div class="row justify-between items-center q-pr-md">
        <div class="row q-gutter-sm items-end">
          <span class="text-h4">
            <template v-if="mainIrrigation.finished">
              {{ FormationUtilities.formatTime(mainIrrigation.start) }}
              -
              {{ FormationUtilities.formatTime(mainIrrigation.end) }}
            </template>
            <template v-else>
              od {{ FormationUtilities.formatTime(mainIrrigation.start) }}
            </template>
          </span>
          <span class="text-h6 text-grey">
            {{ FormationUtilities.formatDateFromNow(mainIrrigation.start) }}
          </span>
        </div>
        <StatusIndicator :active="!mainIrrigation.finished" />
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
      Brak przeszłych uruchomień
    </AppCardSection>
    <q-separator />
    <q-expansion-item v-if="otherIrrigations.length > 0" label="Więcej" dense>
      <q-markup-table flat>
        <tbody>
          <tr v-for="irrigation in otherIrrigations" :key="irrigation.id">
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
              <template v-else>
                od {{ FormationUtilities.formatTime(irrigation.start) }}
              </template>
            </td>
            <td>
              <StatusIndicator v-if="!irrigation.finished" active />
            </td>
          </tr>
        </tbody>
      </q-markup-table>
    </q-expansion-item>
  </AppCard>
</template>
<script setup lang="ts">
import { FormationUtilities } from 'src/utilities/FormationUtilities';
import StatusIndicator from 'components/Status/StatusIndicator.vue';
import { useDashboardStore } from 'pages/Dashboard/_composables/useDashboardStore';
import { computed } from 'vue';

const { irrigations } = useDashboardStore();

const mainIrrigation = computed(() => irrigations.value.at(0));
const otherIrrigations = computed(() => irrigations.value.slice(1));
</script>
