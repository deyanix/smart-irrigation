<template>
  <q-date v-model="modelText" :mask="format" :options="checkDate" />
</template>
<script setup lang="ts">
import { useDateTextModel } from 'components/AppInput/_composables/useDateTextModel';
import {
  AppDateDefaultProps,
  AppDateProps,
} from 'components/AppInput/_types/AppDateProps';
import dayjs from 'dayjs';
import { useDateValidator } from 'components/AppInput/_composables/useDateValidator';

const props = withDefaults(defineProps<AppDateProps>(), AppDateDefaultProps);
const validateDate = useDateValidator(props);

const modelDate = defineModel<Date>();

const { modelText } = useDateTextModel(modelDate, () => props.format);

function checkDate(value: string): boolean {
  const date = dayjs(value, 'YYYY/MM/DD').toDate();
  return validateDate(date);
}
</script>
