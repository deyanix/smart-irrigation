<template>
  <q-btn flat round icon="mdi-pause" @click="onStop" />
  <q-btn flat round icon="mdi-play" @click="onStart" />
</template>
<script setup lang="ts">
import { Dialog } from 'quasar';
import SectionPauseDialog from 'pages/Section/_dialogs/SectionPauseDialog.vue';
import SectionStartDialog from 'pages/Section/_dialogs/SectionStartDialog.vue';
import { SectionModel } from 'src/api/Section';

const props = defineProps<{
  section?: SectionModel;
}>();

const $emit = defineEmits<{
  (e: 'change'): void;
}>();

function onStop() {
  Dialog.create({
    component: SectionPauseDialog,
    componentProps: {
      section: props.section,
    },
  }).onOk(() => $emit('change'));
}

function onStart() {
  Dialog.create({
    component: SectionStartDialog,
    componentProps: {
      section: props.section,
    },
  }).onOk(() => $emit('change'));
}
</script>
