<template>
  <div class="row q-col-gutter-md">
    <div class="col-12">
      <AppDateTimeInput
        v-model="modelFrom"
        label="Od"
        date-from-now
        :rules="[requireRule()]"
        :date-to="modelTo"
        exclusive-rules
        :disable="disable"
      />
    </div>
    <div class="col-12">
      <AppDateTimeInput
        v-model="modelTo"
        label="Do"
        :rules="[requireRule()]"
        :date-from="modelFrom"
        exclusive-rules
        :disable="disable"
      />
    </div>
  </div>
</template>
<script setup lang="ts">
import { useAppValidation } from 'src/composables/useAppValidation';

const { requireRule } = useAppValidation();

defineProps<{ disable?: boolean }>();

const modelFrom = defineModel<Date>('from', { required: true });
const modelTo = defineModel<Date>('to', { required: true });
</script>
