<template>
  <div class="row q-col-gutter-md">
    <div class="col-12">
      <AppDateTimeInput
        v-model="date"
        label="Data"
        mode="date"
        date-from-now
        :rules="[requireRule()]"
        :disable="disable"
      />
    </div>
    <div class="col-12 col-md-6">
      <AppDateTimeInput
        v-model="timeFrom"
        label="Od"
        mode="time"
        :rules="[requireRule()]"
        :disable="disable"
      />
    </div>
    <div class="col-12 col-md-6">
      <AppDateTimeInput
        v-model="timeTo"
        label="Do"
        mode="time"
        :rules="[requireRule()]"
        :disable="disable"
      />
    </div>
  </div>
</template>
<script setup lang="ts">
import { useAppValidation } from 'src/composables/useAppValidation';
import { ref, watch } from 'vue';
import { DateUtilities } from 'src/utilities/DateUtilities';
import dayjs from 'dayjs';

const { requireRule } = useAppValidation();

defineProps<{ disable?: boolean }>();

const modelFrom = defineModel<Date>('from', { required: true });
const modelTo = defineModel<Date>('to', { required: true });

const date = ref<Date>(new Date());
const timeFrom = ref<Date>(new Date());
const timeTo = ref<Date>(new Date());

function getDateFrom(): Date {
  return DateUtilities.getDateWithTime(date.value, timeFrom.value);
}

function getDateTo(): Date {
  const datetime = DateUtilities.getDateWithTime(date.value, timeTo.value);
  const datetimeWrapper = dayjs(datetime);
  if (datetimeWrapper.isBefore(getDateFrom())) {
    return datetimeWrapper.add(1, 'day').toDate();
  } else {
    return datetime;
  }
}

watch([date, timeFrom], () => {
  modelFrom.value = getDateFrom();
});

watch([date, timeTo], () => {
  modelTo.value = getDateTo();
});

watch(modelFrom, () => {
  const dateFrom = dayjs(getDateFrom());
  if (!dateFrom.isSame(modelFrom.value)) {
    date.value = modelFrom.value;
    timeFrom.value = modelFrom.value;
  }
});

watch(modelTo, () => {
  const dateTo = dayjs(getDateTo());
  if (!dateTo.isSame(modelTo.value)) {
    timeTo.value = modelTo.value;
  }
});
</script>
