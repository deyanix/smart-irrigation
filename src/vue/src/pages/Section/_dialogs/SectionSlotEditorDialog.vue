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
                :disable="loading"
                :rules="[requireRule()]"
              />
            </div>
            <div class="col-12 col-md-6">
              <AppDateTimeInput
                v-model="model.end"
                mode="time"
                label="Do"
                :disable="loading"
                :rules="[requireRule()]"
              />
            </div>
            <div class="col-12">
              <AppWeekday v-model="model.weekdays" :disable="loading" />
            </div>
          </div>
        </AppDialogSection>
        <AppDialogActions>
          <q-btn label="Anuluj" flat rounded v-close-popup :disable="loading" />
          <q-btn
            v-if="editMode"
            label="Usuń"
            flat
            rounded
            @click="onDelete"
            :disable="loading"
            :loading="deleteLoading"
          />
          <q-btn
            type="submit"
            label="Zapisz"
            color="primary"
            rounded
            unelevated
            :disable="loading"
            :loading="submitLoading"
          />
        </AppDialogActions>
      </q-form>
    </AppDialogWrapper>
  </q-dialog>
</template>
<script setup lang="ts">
import { useDialogPluginComponent } from 'quasar';
import { computed, ref } from 'vue';
import {
  SectionSlotModel,
  SectionSlotService,
  SectionSlotUpdate,
} from 'src/api/SectionSlot';
import { useAppValidation } from 'src/composables/useAppValidation';

const { dialogRef, onDialogOK } = useDialogPluginComponent();
const { requireRule } = useAppValidation();

const props = defineProps<{
  sectionId?: number;
  slot: SectionSlotModel | SectionSlotUpdate;
}>();

const model = ref<SectionSlotUpdate>(props.slot);
const editMode = computed(() => SectionSlotService.isModel(props.slot));

const submitLoading = ref<boolean>(false);
const deleteLoading = ref<boolean>(false);
const loading = computed(() => submitLoading.value || deleteLoading.value);

async function onDelete() {
  deleteLoading.value = true;
  try {
    if (SectionSlotService.isModel(props.slot)) {
      await SectionSlotService.delete(props.slot.id);
    }
    onDialogOK();
  } finally {
    submitLoading.value = false;
  }
}

async function onSubmit() {
  submitLoading.value = true;
  try {
    if (SectionSlotService.isModel(props.slot)) {
      await SectionSlotService.update(props.slot.id, model.value);
    } else if (props.sectionId) {
      await SectionSlotService.create(props.sectionId, model.value);
    }
    onDialogOK();
  } finally {
    submitLoading.value = false;
  }
}
</script>
