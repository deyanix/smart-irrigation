<template>
  <q-slide-transition>
    <div v-show="!showAdvanced">
      <div class="row q-col-gutter-md">
        <div class="col-12">
          <q-select
            v-model="dateFromOption"
            label="Czas rozpoczęcia"
            outlined
            dense
            :options="fromOptions"
            :rules="[requireRule()]"
            hide-bottom-space
            :disable="disable"
          />
        </div>
        <div class="col-12">
          <q-select
            v-model="dateToOption"
            label="Czas trwania"
            outlined
            dense
            :options="toOptions"
            :rules="[requireRule()]"
            hide-bottom-space
            :disable="disable"
          />
        </div>
      </div>
    </div>
  </q-slide-transition>
  <q-slide-transition>
    <div v-show="showAdvanced">
      <SectionScheduleAdvancedEditor
        v-if="!multiday"
        v-model:from="dateFrom"
        v-model:to="dateTo"
        :disable="!showAdvanced || disable"
      />
      <SectionScheduleAdvancedMultidayEditor
        v-else
        v-model:from="dateFrom"
        v-model:to="dateTo"
        :disable="!showAdvanced || disable"
      />
    </div>
  </q-slide-transition>
  <div class="q-mt-md">
    <q-checkbox v-model="showAdvanced" label="Opcje zaawansowane" dense />
  </div>
</template>
<script setup lang="ts">
import { DurationOption } from 'pages/Section/_types/DurationOptions';
import { useAppValidation } from 'src/composables/useAppValidation';
import { ref, watch } from 'vue';
import dayjs from 'dayjs';
import { SectionScheduleUpdate } from 'src/api/SectionSchedule/SectionScheduleTypes';
import SectionScheduleAdvancedEditor from 'pages/Section/_components/SectionScheduleAdvancedEditor.vue';
import SectionScheduleAdvancedMultidayEditor from 'pages/Section/_components/SectionScheduleAdvancedMultidayEditor.vue';

const { requireRule } = useAppValidation();

const props = withDefaults(
  defineProps<{
    fromOptions: DurationOption[];
    toOptions: DurationOption[];
    defaultFromOption: string;
    defaultToOption: string;
    multiday?: boolean;
    disable?: boolean;
  }>(),
  { multiday: false, disable: false }
);

const model = defineModel<SectionScheduleUpdate>({ required: true });

const showAdvanced = ref<boolean>(false);

const dateFrom = ref(new Date());
const dateTo = ref(new Date());
const dateFromOption = ref<DurationOption>();
const dateToOption = ref<DurationOption>();

function getDateFrom(): Date | undefined {
  if (showAdvanced.value) {
    return dateFrom.value;
  } else if (dateFromOption.value) {
    return dateFromOption.value.mutation(dayjs());
  }
  return undefined;
}

function getDateTo(dateFrom?: Date): Date | undefined {
  if (showAdvanced.value) {
    return dateTo.value;
  } else if (dateFrom && dateToOption.value) {
    return dateToOption.value.mutation(dayjs(dateFrom));
  }
  return undefined;
}

watch([showAdvanced, dateFrom, dateTo, dateFromOption, dateToOption], () => {
  const dateFrom = getDateFrom();
  const dateTo = getDateTo(dateFrom);

  model.value.start = dateFrom;
  model.value.end = dateTo;
});

watch(
  showAdvanced,
  () => {
    const defaultFromOption = props.fromOptions.find(
      (o) => o.value === props.defaultFromOption
    );
    const defaultToOption = props.toOptions.find(
      (o) => o.value === props.defaultToOption
    );

    dateFromOption.value = defaultFromOption;
    dateToOption.value = defaultToOption;

    const now = dayjs();
    const roundedMinute = Math.ceil(dayjs().minute() / 5) * 5;
    const newTimeFrom = now.startOf('hour').add(roundedMinute, 'minute');
    const newTimeTo = defaultToOption?.mutation(newTimeFrom);

    dateFrom.value = newTimeFrom.toDate();
    dateTo.value = newTimeTo ?? newTimeFrom.toDate();
  },
  { immediate: true }
);
</script>
