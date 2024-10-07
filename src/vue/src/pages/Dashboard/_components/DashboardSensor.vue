<template>
  <AppCard>
    <AppCardHeader :label="sensor.name" />
    <q-markup-table>
      <tbody>
        <tr v-for="item in sensor.items" :key="item.id">
          <td class="col">
            {{ item.name }}
          </td>
          <td>
            <template v-if="item.lastMeasurementValue">
              {{ item.lastMeasurementValue }}
              {{ item.measurementUnitSymbol }}
            </template>
            <span v-else class="text-grey"> (brak) </span>
          </td>
        </tr>
      </tbody>
    </q-markup-table>
  </AppCard>
</template>
<script setup lang="ts">
import { SensorModel } from 'src/api/Sensor';
defineProps<{
  sensor: SensorModel;
}>();
</script>
