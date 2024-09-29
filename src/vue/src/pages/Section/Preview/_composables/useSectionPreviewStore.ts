import { SectionModel, SectionService } from 'src/api/Section';
import { inject, InjectionKey, onBeforeMount, provide, Ref, ref } from 'vue';
import {
  IrrigationModel,
  SectionIrrigationModel,
} from 'src/api/SectionIrrigation/SectionIrrigationTypes';
import { SectionIrrigationService } from 'src/api/SectionIrrigation/SectionIrrigationService';
import { SectionSlotModel, SectionSlotService } from 'src/api/SectionSlot';
import { Loading } from 'quasar';

export type SectionPreviewStore = ReturnType<typeof createSectionPreviewStore>;

export function createSectionPreviewStore(sectionId: Ref<number>) {
  const section = ref<SectionModel>();
  const slots = ref<SectionSlotModel[]>([]);
  const upcomingIrrigations = ref<SectionIrrigationModel[]>([]);
  const irrigations = ref<IrrigationModel[]>([]);

  async function fetchStore() {
    Loading.show({
      group: 'SectionPreviewStore-fetch',
      message: 'Wczytywanie danych sekcji...',
    });
    try {
      [
        section.value,
        slots.value,
        upcomingIrrigations.value,
        irrigations.value,
      ] = await Promise.all([
        SectionService.getSection(sectionId.value),
        SectionSlotService.getSlots(sectionId.value),
        SectionIrrigationService.getUpcomingIrrigations(sectionId.value),
        SectionIrrigationService.getIrrigations(sectionId.value, {
          pageSize: 3,
        }).then((result) => result.rows),
      ]);
    } finally {
      Loading.hide('SectionPreviewStore-fetch');
    }
  }

  onBeforeMount(() => fetchStore());

  return {
    section,
    slots,
    upcomingIrrigations,
    irrigations,
    fetchStore,
  };
}

export const SectionPreviewStoreInjectionKey: InjectionKey<SectionPreviewStore> =
  Symbol('Section preview store');

export function defineSectionPreviewStore(
  sectionId: Ref<number>
): SectionPreviewStore {
  const store = createSectionPreviewStore(sectionId);
  provide(SectionPreviewStoreInjectionKey, store);
  return store;
}

export function useSectionPreviewStore() {
  const store = inject(SectionPreviewStoreInjectionKey);
  if (!store) throw new Error('Not found SectionPreviewStore');
  return store;
}
