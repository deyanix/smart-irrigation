<template>
  <q-input
    v-bind="props"
    v-model="modelText"
    outlined
    dense
    :mask="modeDefinition.inputMask"
    @focus="onFocus"
    @blur="onBlur"
    :rules="[dateRule(props, modeDefinition.format), ...propsRules()]"
    reactive-rules
    hide-bottom-space
  >
    <template #append>
      <q-btn v-if="modeDefinition.showCalendar" icon="event" round dense flat>
        <q-popup-proxy>
          <AppDate
            v-model="modelDate"
            :date-from="dateFrom"
            :date-from-now="dateFromNow"
            :date-to="dateTo"
            :date-to-now="dateToNow"
            :exclusive-rules="exclusiveRules"
          />
        </q-popup-proxy>
      </q-btn>
      <q-btn
        v-if="modeDefinition.showClock"
        icon="access_time"
        round
        dense
        flat
      >
        <q-popup-proxy>
          <q-time v-model="modelText" :mask="modeDefinition.format" />
        </q-popup-proxy>
      </q-btn>
    </template>
  </q-input>
</template>
<script setup lang="ts">
import { useDateTextModel } from 'components/AppInput/_composables/useDateTextModel';
import {
  AppDateTimeDefaultProps,
  AppDateTimeProps,
} from 'components/AppInput/_types/AppDateTimeProps';
import { computed } from 'vue';
import { AppDateTimeModes } from 'components/AppInput/_types/AppDateTimeMode';
import AppDate from 'components/AppInput/AppDate.vue';
import { dateRule } from 'components/AppInput/_composables/useDateValidator';
import { ValidationRule } from 'quasar';

const props = withDefaults(
  defineProps<AppDateTimeProps>(),
  AppDateTimeDefaultProps
);

const modeDefinition = computed(() => AppDateTimeModes[props.mode]);

const modelDate = defineModel<Date>();
const { modelText, onFocus, onBlur } = useDateTextModel(
  modelDate,
  () => modeDefinition.value.format
);

function propsRules(): ValidationRule[] {
  return props.rules.map((fn) =>
    fn instanceof Function ? (_, p2) => fn(modelDate.value, p2) : fn
  );
}
</script>
