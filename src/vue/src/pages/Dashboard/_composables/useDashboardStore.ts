import { inject, InjectionKey, onBeforeMount, provide, ref } from 'vue';
import {
  IrrigationModel,
  IrrigationService,
  UpcomingIrrigationModel,
} from 'src/api/Irrigation';
import { Loading } from 'quasar';
import { useInstallationId } from 'src/composables/useInstallationId';
import { SensorModel, SensorService } from 'src/api/Sensor';

export type DashboardStore = ReturnType<typeof createDashboardStore>;

export function createDashboardStore() {
  const installationId = useInstallationId();
  const upcomingIrrigations = ref<UpcomingIrrigationModel[]>([]);
  const irrigations = ref<IrrigationModel[]>([]);
  const sensors = ref<SensorModel[]>();

  async function fetchStore() {
    Loading.show({
      group: 'DashboardStore-fetch',
      message: 'Wczytywanie danych sekcji...',
    });
    try {
      [upcomingIrrigations.value, irrigations.value, sensors.value] =
        await Promise.all([
          IrrigationService.getUpcomingIrrigationsByInstallation(
            installationId.value
          ),
          IrrigationService.searchByInstallation(installationId.value, {
            pageSize: 6,
          }).then((result) => result.rows),
          SensorService.getSensors(installationId.value),
        ]);
    } finally {
      Loading.hide('DashboardStore-fetch');
    }
  }

  onBeforeMount(() => fetchStore());

  return {
    upcomingIrrigations,
    irrigations,
    sensors,
    fetchStore,
  };
}

export const DashboardStore: InjectionKey<DashboardStore> =
  Symbol('Dashboard store');

export function defineDashboardStore(): DashboardStore {
  const store = createDashboardStore();
  provide(DashboardStore, store);
  return store;
}

export function useDashboardStore() {
  const store = inject(DashboardStore);
  if (!store) throw new Error('Not found DashboardStore');
  return store;
}
