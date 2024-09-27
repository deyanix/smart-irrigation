<template>
  <q-dialog ref="dialogRef" persistent>
    <AppDialogWrapper width="560px">
      <q-form @submit="onSubmit">
        <AppDialogHeader title="Zatrzymanie sekcji" />
        <AppDialogSection>
          <q-checkbox
            v-model="stopOnlyCurrent"
            label="Zatrzymaj tylko obecne zraszczanie"
            dense
            class="q-mb-md"
          />
          <q-slide-transition>
            <div v-show="!stopOnlyCurrent">
              <SectionScheduleEditor
                v-model="model"
                :from-options="pauseFromOptions"
                :to-options="pauseToOptions"
                default-from-option="now"
                default-to-option="end-day"
                multiday
                :disable="stopOnlyCurrent"
              />
            </div>
          </q-slide-transition>
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
import { SectionModel, SectionService } from 'src/api/Section';
import { ref } from 'vue';
import { SectionScheduleUpdate } from 'src/api/SectionSchedule/SectionScheduleTypes';
import SectionScheduleEditor from 'pages/Section/_components/SectionScheduleEditor.vue';
import {
  pauseFromOptions,
  pauseToOptions,
} from 'pages/Section/_types/DurationOptions';

const { dialogRef, onDialogOK } = useDialogPluginComponent();

const props = defineProps<{
  section: SectionModel;
}>();

const model = ref<SectionScheduleUpdate>({
  state: false,
});
const stopOnlyCurrent = ref<boolean>(true);

async function onSubmit() {
  Loading.show({
    group: 'SectionStartDialog-submit',
    message: 'Ustawianie harmonogramu...',
  });
  try {
    if (stopOnlyCurrent.value) {
      await SectionService.stop(props.section.id);
    } else {
      await SectionScheduleService.create(props.section.id, model.value);
    }
    onDialogOK();
  } finally {
    Loading.hide('SectionStartDialog-submit');
  }
}
</script>
