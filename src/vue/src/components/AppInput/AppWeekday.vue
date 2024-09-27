<template>
  <div class="row q-col-gutter-sm">
    <div
      v-for="weekday in DayOfWeeks"
      :key="weekday.value"
      class="col-6 col-sm-3"
    >
      <q-checkbox
        :label="weekday.label"
        :model-value="isSelected(weekday)"
        dense
        @update:model-value="toggleSelected(weekday)"
      />
    </div>
  </div>
</template>
<script setup lang="ts">
import { DayOfWeek, DayOfWeeks } from 'src/utilities/_types/DayOfWeeks';
import { toggle } from 'radashi';

const model = defineModel<number[]>({
  required: true,
});

function isSelected(weekday: DayOfWeek): boolean {
  return model.value.includes(weekday.nativeValue);
}

function toggleSelected(weekday: DayOfWeek): void {
  model.value = toggle(model.value, weekday.nativeValue);
}
</script>
