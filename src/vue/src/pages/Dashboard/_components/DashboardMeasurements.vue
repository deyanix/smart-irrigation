<template>
  <AppCard>
    <apexchart :series="series" :options="options" width="100%" height="200" />
  </AppCard>
</template>
<script setup lang="ts">
import { onBeforeMount, ref, shallowRef } from 'vue';
import { MeasurementService } from 'src/api/Measurement/MeasurementService';
import { ApexOptions } from 'apexcharts';
import dayjs from 'dayjs';

const dateTo = new Date();
const dateFrom = dayjs(dateTo).add(-4, 'day').toDate();

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
});

async function fetchMeasurements() {
  const data = await MeasurementService.getMeasurements(2, {
    dateFrom,
    dateTo,
  });

  for (let i = 0; i < data.length - 1; i++) {
    const currentDate = data[i].date;
    const nextDate = data[i + 1].date;
    const diffDays =
      (nextDate.getTime() - currentDate.getTime()) / (1000 * 60 * 60);

    if (diffDays > 1) {
      data.splice(i + 1, 0, { date: currentDate, value: null });
      i++;
    }
  }
  series.value = [
    {
      data: data.map((m) => [m.date.getTime(), m.value]),
    },
  ];
}

onBeforeMount(() => {
  fetchMeasurements();
});
</script>
