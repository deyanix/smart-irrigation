<template>
  <q-dialog ref="dialogRef" persistent>
    <AppDialogWrapper width="560px">
      <q-form @submit="onSubmit" greedy>
        <AppDialogHeader title="Harmonogram" />
        <AppDialogSection>
          <div class="row q-col-gutter-md">
            <div class="col-12 col-md-6">
              <AppDateTimeInput
                v-model="model.start"
                mode="time"
                label="Od"
                :rules="[requireRule()]"
              />
            </div>
            <div class="col-12 col-md-6">
              <AppDateTimeInput
                v-model="model.end"
                mode="time"
                label="Do"
                :rules="[requireRule()]"
              />
            </div>
            <div class="col-12">
              <AppWeekday v-model="model.weekdays" />
            </div>
          </div>
        </AppDialogSection>
        <AppDialogActions>
          <q-btn label="Anuluj" flat rounded v-close-popup />
          <q-btn v-if="editMode" label="Usuń" flat rounded @click="onDelete" />
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
import { computed, ref } from 'vue';
import {
  SectionSlotModel,
  SectionSlotService,
  SectionSlotUpdate,
} from 'src/api/SectionSlot';
import { useAppValidation } from 'src/composables/useAppValidation';
import { SectionModel } from 'src/api/Section';

const { dialogRef, onDialogOK } = useDialogPluginComponent();
const { requireRule } = useAppValidation();

const props = defineProps<{
  section: SectionModel;
  slot: SectionSlotModel | SectionSlotUpdate;
}>();

const model = ref<SectionSlotUpdate>(props.slot);
const editMode = computed(() => SectionSlotService.isModel(props.slot));

async function onDelete() {
  Loading.show({
    group: 'SectionSlotEditorDialog-delete',
    message: 'Usuwanie harmonogramu...',
  });
  try {
    if (SectionSlotService.isModel(props.slot)) {
      await SectionSlotService.delete(props.slot.id);
    }
    onDialogOK();
  } finally {
    Loading.hide('SectionSlotEditorDialog-delete');
  }
}

async function onSubmit() {
  Loading.show({
    group: 'SectionSlotEditorDialog-submit',
    message: 'Zapisywanie harmonogramu...',
  });
  try {
    if (SectionSlotService.isModel(props.slot)) {
      await SectionSlotService.update(props.slot.id, model.value);
    } else {
      await SectionSlotService.create(props.section.id, model.value);
    }
    onDialogOK();
  } finally {
    Loading.hide('SectionSlotEditorDialog-submit');
  }
}
</script>
