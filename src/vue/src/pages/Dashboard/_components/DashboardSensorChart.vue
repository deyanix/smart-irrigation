<template>
  <apexchart :series="series" :options="options" width="100%" height="250" />
</template>
<script setup lang="ts">
import { onBeforeMount, ref, shallowRef } from 'vue';
import { MeasurementService } from 'src/api/Measurement/MeasurementService';
import { ApexOptions } from 'apexcharts';
import dayjs from 'dayjs';
import { SensorItemModel } from 'src/api/Sensor';

const dateTo = new Date();
const dateFrom = dayjs(dateTo).add(-4, 'day').toDate();

const props = defineProps<{
  sensorItem: SensorItemModel;
}>();

const series = shallowRef<ApexAxisChartSeries>([]);

const options = ref<ApexOptions>({
  chart: {
    zoom: { enabled: false },
    toolbar: { show: false },
  },
  stroke: {
    curve: 'smooth',
    lineCap: 'butt',
    colors: undefined,
    width: [2, 0],
  },
  tooltip: {
    x: {
      format: 'yyyy-MM-dd HH:mm',
    },
  },
  xaxis: {
    type: 'datetime',
    min: dateFrom.getTime(),
    max: dateTo.getTime(),
    labels: {
      format: 'd MMM',
    },
  },
  yaxis: {
    title: {
      text: `${props.sensorItem.measurementUnitName} [${props.sensorItem.measurementUnitSymbol}]`,
    },
  },
});

async function fetchMeasurements() {
  const data = await MeasurementService.getMeasurements(props.sensorItem.id, {
    dateFrom,
    dateTo,
  });

  series.value = [
    {
      data: data.map((m): [number, number | null] => [
        m.date.getTime(),
        m.value,
      ]),
    },
  ];
}

onBeforeMount(() => {
  fetchMeasurements();
});
</script>
