<template>
  <q-dialog ref="dialogRef" persistent>
    <AppDialogWrapper width="560px">
      <q-form @submit="onSubmit">
        <AppDialogHeader title="Uruchomienie sekcji" />
        <AppDialogSection>
          <SectionScheduleEditor
            v-model="model"
            :from-options="startFromOptions"
            :to-options="startToOptions"
            default-from-option="now"
            default-to-option="15min"
          />
        </AppDialogSection>
        <AppDialogActions>
          <q-btn label="Anuluj" flat rounded v-close-popup />
          <q-btn
            type="submit"
            label="Zapisz"
            color="primary"
            rounded
            unelevated
          />
        </AppDialogActions>
      </q-form>
    </AppDialogWrapper>
  </q-dialog>
</template>
<script setup lang="ts">
import { Loading, useDialogPluginComponent } from 'quasar';
import { SectionScheduleService } from 'src/api/SectionSchedule/SectionScheduleService';
import { SectionModel } from 'src/api/Section';
import { ref } from 'vue';
import { SectionScheduleUpdate } from 'src/api/SectionSchedule/SectionScheduleTypes';
import SectionScheduleEditor from 'pages/Section/_components/SectionScheduleEditor.vue';
import {
  startFromOptions,
  startToOptions,
} from 'pages/Section/_types/DurationOptions';

const { dialogRef, onDialogOK } = useDialogPluginComponent();

const props = defineProps<{
  section: SectionModel;
}>();

const model = ref<SectionScheduleUpdate>({
  state: true,
});

async function onSubmit() {
  Loading.show({
    group: 'SectionStartDialog-submit',
    message: 'Ustawianie harmonogramu...',
  });
  try {
    await SectionScheduleService.create(props.section.id, model.value);
    onDialogOK();
  } finally {
    Loading.hide('SectionStartDialog-submit');
  }
}
</script>
