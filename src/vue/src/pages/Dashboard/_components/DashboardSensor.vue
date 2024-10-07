<template>
  <AppCard>
    <AppCardHeader :label="sensor.name" />
    <q-tabs v-model="tab" align="left">
      <q-tab
        v-for="item in sensor.items"
        :key="item.id"
        :label="item.name"
        :name="item.id"
      />
    </q-tabs>
    <q-tab-panels v-model="tab">
      <q-tab-panel
        v-for="item in sensor.items"
        :key="item.id"
        :name="item.id"
        class="q-pa-xs"
      >
        <DashboardSensorChart :sensor-item="item" />
      </q-tab-panel>
    </q-tab-panels>
  </AppCard>
</template>
<script setup lang="ts">
import { SensorModel } from 'src/api/Sensor';
import { ref } from 'vue';
import DashboardSensorChart from 'pages/Dashboard/_components/DashboardSensorChart.vue';

const props = defineProps<{
  sensor: SensorModel;
}>();

const tab = ref(props.sensor.items.at(0)?.id);
</script>
<style lang="scss">
.q-tab-panels,
.q-tab-panels > .q-panel {
  overflow: initial;
}
</style>
