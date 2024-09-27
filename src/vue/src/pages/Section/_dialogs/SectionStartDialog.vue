<template>
  <q-dialog ref="dialogRef" persistent>
    <AppDialogWrapper width="560px">
      <q-form @submit="onSubmit">
        <AppDialogHeader title="Uruchomienie sekcji" />
        <AppDialogSection>
          <q-slide-transition>
            <div v-show="!showAdvanced">
              <div class="row q-col-gutter-md">
                <div class="col-12">
                  <q-select
                    v-model="dateFromOption"
                    label="Czas uruchomienia"
                    outlined
                    dense
                    :options="startFromOptions"
                    :disable="loading"
                  />
                </div>
                <div class="col-12">
                  <q-select
                    v-model="dateToOption"
                    label="Czas trwania"
                    outlined
                    dense
                    :options="startToOptions"
                    :disable="loading"
                  />
                </div>
              </div>
            </div>
          </q-slide-transition>
          <q-slide-transition>
            <div v-show="showAdvanced">
              <div class="row q-col-gutter-md">
                <div class="col-12">
                  <AppDateTimeInput
                    v-model="date"
                    label="Data"
                    mode="date"
                    date-from-now
                    :rules="[requireRule()]"
                    :disable="loading"
                  />
                </div>
                <div class="col-12 col-md-6">
                  <AppDateTimeInput
                    v-model="timeFrom"
                    label="Od"
                    mode="time"
                    :rules="[requireRule()]"
                    :disable="loading"
                  />
                </div>
                <div class="col-12 col-md-6">
                  <AppDateTimeInput
                    v-model="timeTo"
                    label="Do"
                    mode="time"
                    :rules="[requireRule()]"
                    :disable="loading"
                  />
                </div>
              </div>
            </div>
          </q-slide-transition>
          <div class="q-mt-md">
            <q-checkbox
              v-model="showAdvanced"
              label="Opcje zaawansowane"
              dense
              :disable="loading"
            />
          </div>
        </AppDialogSection>
        <AppDialogActions>
          <q-btn label="Anuluj" flat rounded v-close-popup :disable="loading" />
          <q-btn
            type="submit"
            label="Zapisz"
            color="primary"
            rounded
            unelevated
            :loading="loading"
          />
        </AppDialogActions>
      </q-form>
    </AppDialogWrapper>
  </q-dialog>
</template>
<script setup lang="ts">
import { Loading, useDialogPluginComponent } from 'quasar';
import { computed, ref, watch } from 'vue';
import {
  DurationOption,
  startFromOptions,
  startToOptions,
} from 'pages/Section/_dialogs/_types/DurationOptions';
import { useAppValidation } from 'src/composables/useAppValidation';
import dayjs from 'dayjs';
import { SectionScheduleService } from 'src/api/SectionSchedule/SectionScheduleService';

const { dialogRef, onDialogOK } = useDialogPluginComponent();
const { requireRule } = useAppValidation();

const props = defineProps<{
  sectionId: number;
}>();

const showAdvanced = ref<boolean>(false);
const loading = ref<boolean>(false);

const date = ref<Date>(new Date());
const timeFrom = ref<Date>(new Date());
const timeTo = ref<Date>(new Date());
const dateFromOption = ref<DurationOption>();
const dateToOption = ref<DurationOption>();

function getDateWithTime(date: Date, time: Date) {
  const timeWrapper = dayjs(time);
  return dayjs(date)
    .startOf('day')
    .add(timeWrapper.hour(), 'hour')
    .add(timeWrapper.minute(), 'minute')
    .toDate();
}

const dateFrom = computed<Date | undefined>(() => {
  if (showAdvanced.value) {
    return getDateWithTime(date.value, timeFrom.value);
  } else if (dateFromOption.value) {
    return dayjs().add(dateFromOption.value.duration).toDate();
  }
  return undefined;
});

const dateTo = computed<Date | undefined>(() => {
  if (showAdvanced.value) {
    const datetime = getDateWithTime(date.value, timeTo.value);
    const datetimeWrapper = dayjs(datetime);
    if (datetimeWrapper.isBefore(dateFrom.value)) {
      return datetimeWrapper.add(1, 'day').toDate();
    } else {
      return datetime;
    }
  } else if (dateFrom.value && dateToOption.value) {
    return dayjs(dateFrom.value).add(dateToOption.value.duration).toDate();
  }
  return undefined;
});

watch(
  showAdvanced,
  () => {
    const now = dayjs();
    const roundedMinute = Math.ceil(dayjs().minute() / 5) * 5;

    const newTimeFrom = now.startOf('hour').add(roundedMinute, 'minute');
    const newTimeTo = newTimeFrom.add(15, 'minute');

    timeFrom.value = newTimeFrom.toDate();
    timeTo.value = newTimeTo.toDate();

    dateFromOption.value = startFromOptions.find((o) => o.value === 'now');
    dateToOption.value = startToOptions.find((o) => o.value === '15min');
  },
  { immediate: true }
);

async function onSubmit() {
  const start = dateFrom.value;
  const end = dateTo.value;
  if (!start || !end) return;

  Loading.show({
    group: 'SectionStartDialog-submit',
    message: 'Ustawianie harmonogramu...',
  });
  try {
    await SectionScheduleService.create(props.sectionId, {
      start: start,
      end: end,
      state: true,
    });
    onDialogOK();
  } finally {
    Loading.hide('SectionStartDialog-submit');
  }
}
</script>
