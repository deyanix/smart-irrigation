<template>
  <q-dialog ref="dialogRef">
    <AppDialogWrapper>
      <AppDialogHeader title="Uruchomienie sekcji" />
      <AppDialogSection>
        <div class="row">
          <div class="col-12">
            <q-select
              model-value=""
              label="Opóźnienie"
              outlined
              dense
              :options="['Teraz', '30 minut', '1 godzina', '3 godzin', 'Inne']"
            />
          </div>
          <div class="col-12">
            <q-select
              model-value=""
              label="Czas trwania"
              outlined
              dense
              :options="[
                '5 minut',
                '10 minut',
                '15 minut',
                '20 minut',
                '30 minut',
                'Inne',
              ]"
            />
          </div>
        </div>
      </AppDialogSection>
      <AppDialogActions>
        <q-btn label="Anuluj" flat rounded v-close-popup />
        <q-btn label="Zapisz" color="primary" rounded unelevated />
      </AppDialogActions>
    </AppDialogWrapper>
  </q-dialog>
</template>
<script setup lang="ts">
import { useDialogPluginComponent } from 'quasar';

const { dialogRef } = useDialogPluginComponent();
</script>
