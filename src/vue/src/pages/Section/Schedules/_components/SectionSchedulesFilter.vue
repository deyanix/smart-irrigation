<template>
  <AppCard>
    <AppCardSection>
      <q-form @submit="$emit('search')" class="row q-col-gutter-md">
        <div class="col-12 col-md-3">
          <AppDateTimeInput v-model="model.from" label="Od" />
        </div>
        <div class="col-12 col-md-3">
          <AppDateTimeInput v-model="model.to" label="Do" />
        </div>
        <div class="col-12 col-md-6 text-right">
          <q-btn
            label="Szukaj"
            type="submit"
            color="primary"
            unelevated
            rounded
          />
        </div>
      </q-form>
    </AppCardSection>
  </AppCard>
</template>
<script setup lang="ts">
import { SectionScheduleSearchRequest } from 'src/api/SectionSchedule/SectionScheduleTypes';

const model = defineModel<SectionScheduleSearchRequest>({ required: true });

const $emit = defineEmits<{
  (e: 'search'): void;
}>();
</script>
