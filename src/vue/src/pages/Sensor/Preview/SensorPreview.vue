<template>
  <q-page padding>
    <AppPageHeader>
      <template #breadcrumbs>
        <AppBreadcrumbs class="col">
          <AppBreadcrumbsEl :to="{ name: 'SensorList' }">
            Czujniki
          </AppBreadcrumbsEl>
          <AppBreadcrumbsEl :loading="!sensor">
            {{ sensor?.name }}
          </AppBreadcrumbsEl>
        </AppBreadcrumbs>
      </template>
    </AppPageHeader>
    <div class="row q-col-gutter-md">
      <div v-for="item in sensor?.items" :key="item.id" class="col-12">
        <AppCard>
          <AppCardHeader :label="item.name">
            <template v-if="item.lastMeasurementValue" #append>
              {{ item.lastMeasurementValue }}
              {{ item.measurementUnitSymbol }}
            </template>
          </AppCardHeader>
          <AppCardSection class="q-px-md q-py-xs">
            <SensorChart
              :sensor-item="item"
              :date-from="dateFrom"
              :date-to="dateTo"
            />
          </AppCardSection>
        </AppCard>
      </div>
    </div>
  </q-page>
</template>
<script setup lang="ts">
import { computed, onBeforeMount, ref } from 'vue';
import { useRoute } from 'vue-router';
import { SensorModel, SensorService } from 'src/api/Sensor';
import SensorChart from 'pages/Sensor/Preview/_components/SensorChart.vue';
import dayjs from 'dayjs';

const dateTo = new Date();
const dateFrom = dayjs(dateTo).add(-4, 'day').toDate();

const $route = useRoute();
const sensorId = computed(() => parseInt($route.params.id as string));

const sensor = ref<SensorModel>();
const loading = ref<boolean>(false);

async function fetch() {
  loading.value = true;
  try {
    sensor.value = await SensorService.getSensor(sensorId.value);
  } finally {
    loading.value = false;
  }
}

onBeforeMount(() => fetch());
</script>
