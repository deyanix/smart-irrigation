<template>
  <AppCard>
    <AppCardHeader label="Harmonogram">
      <template #append>
        <q-btn icon="add" flat round dense @click="onCreate" />
      </template>
    </AppCardHeader>
    <q-markup-table v-if="slots.length > 0">
      <tbody>
        <tr v-for="slot in slots" :key="slot.id">
          <td>
            {{ FormationUtilities.formatWeekdays(slot.weekdays) }}
          </td>
          <td>
            {{ FormationUtilities.formatTime(slot.start) }}
            -
            {{ FormationUtilities.formatTime(slot.end) }}
          </td>
          <td class="text-right">
            <q-btn icon="edit" flat round dense @click="onUpdate(slot)" />
          </td>
        </tr>
      </tbody>
    </q-markup-table>
    <q-card-section v-else class="text-center">
      Nie zdefiniowanego harmonogramu
    </q-card-section>
  </AppCard>
</template>
<script setup lang="ts">
import { Dialog } from 'quasar';
import SectionSlotEditorDialog from 'pages/Section/_dialogs/SectionSlotEditorDialog.vue';
import { SectionSlotModel, SectionSlotService } from 'src/api/SectionSlot';
import { onBeforeMount, ref } from 'vue';
import { FormationUtilities } from 'src/utilities/FormationUtilities';
import { cloneDeep } from 'radashi';

const props = defineProps<{ sectionId: number }>();

const slots = ref<SectionSlotModel[]>([]);

function onCreate() {
  Dialog.create({
    component: SectionSlotEditorDialog,
    componentProps: {
      sectionId: props.sectionId,
      slot: SectionSlotService.createEmptyUpdate(),
    },
  }).onOk(() => fetchSlots());
}

function onUpdate(slot: SectionSlotModel) {
  Dialog.create({
    component: SectionSlotEditorDialog,
    componentProps: {
      sectionId: props.sectionId,
      slot: cloneDeep(slot),
    },
  }).onOk(() => fetchSlots());
}

async function fetchSlots(): Promise<void> {
  slots.value = await SectionSlotService.getList(props.sectionId);
}

onBeforeMount(() => fetchSlots());
</script>
