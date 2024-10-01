<template>
  <q-dialog ref="dialogRef" persistent>
    <AppDialogWrapper width="560px">
      <AppDialogHeader title="Szczegóły uruchomienia" />
      <q-list>
        <q-item
          v-for="(source, index) in irrigation.sources"
          :key="source.scheduleId ?? source.slotId ?? `index-${index}`"
        >
          <q-item-section>
            {{ FormationUtilities.formatDate(source.start) }}
          </q-item-section>
          <q-item-section>
            {{ FormationUtilities.formatTime(source.start) }}
            -
            {{ FormationUtilities.formatTime(source.end) }}
          </q-item-section>
          <q-item-section avatar>
            <q-avatar>
              <q-icon v-if="source.slotId" name="history" size="sm">
                <q-tooltip>Harmonogram</q-tooltip>
              </q-icon>
              <q-icon
                v-else-if="source.scheduleId && source.state"
                name="mdi-play"
                size="sm"
              >
                <q-tooltip>Zaplanowane uruchomienie</q-tooltip>
              </q-icon>
              <q-icon
                v-else-if="source.scheduleId && !source.state"
                name="mdi-pause"
                size="sm"
              >
                <q-tooltip>Zaplanowane zatrzymanie</q-tooltip>
              </q-icon>
            </q-avatar>
          </q-item-section>
        </q-item>
      </q-list>
    </AppDialogWrapper>
  </q-dialog>
</template>
<script setup lang="ts">
import { useDialogPluginComponent } from 'quasar';
import { UpcomingIrrigationModel } from 'src/api/Irrigation';
import { FormationUtilities } from 'src/utilities/FormationUtilities';

const { dialogRef } = useDialogPluginComponent();

defineProps<{ irrigation: UpcomingIrrigationModel }>();
</script>
