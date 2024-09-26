import { computed, MaybeRefOrGetter, ModelRef, ref, toValue, watch } from 'vue';
import dayjs from 'dayjs';

export function useDateTextModel(
  modelDate: ModelRef<Date | undefined>,
  formatGetter: MaybeRefOrGetter<string>
) {
  const modelText = ref<string>('');
  const focus = ref<boolean>(false);

  const format = computed(() => toValue(formatGetter));

  const wrapDate = (date: Date | undefined) => dayjs(date);
  const wrapDateText = (text: string) => dayjs(text, toValue(format.value));

  function refreshFromDate() {
    if (modelDate.value instanceof Date) {
      const modelDateWrapper = wrapDate(modelDate.value);
      if (modelDateWrapper.isValid()) {
        modelText.value = modelDateWrapper.format(toValue(formatGetter));
      }
    } else {
      modelText.value = '';
    }
  }

  function refreshFromText() {
    const modelTextWrapper = wrapDateText(modelText.value);
    const newModelText = modelTextWrapper.format(format.value);
    console.log('refreshFromText', modelText.value, newModelText, format.value);

    if (modelTextWrapper.isValid() && modelText.value === newModelText) {
      modelDate.value = modelTextWrapper.toDate();
    } else {
      modelDate.value = undefined;
    }
  }

  function onFocus() {
    focus.value = true;
  }

  function onBlur() {
    focus.value = false;
  }

  watch(
    modelDate,
    () => {
      if (!toValue(focus)) {
        refreshFromDate();
      }
    },
    { immediate: true }
  );
  watch(modelText, () => refreshFromText());

  return { modelText, onBlur, onFocus, refreshFromText, refreshFromDate };
}
