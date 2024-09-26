import { useInstallationStore } from 'stores/installation';
import { computed } from 'vue';

export function useInstallationId() {
  const installationStore = useInstallationStore();
  return computed(() => installationStore.currentInstallation?.id);
}
