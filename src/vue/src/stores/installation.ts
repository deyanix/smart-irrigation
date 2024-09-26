import { defineStore } from 'pinia';
import { ref } from 'vue';
import { InstallationModel } from 'src/api/Installation/InstallationTypes';
import { useInstallationService } from 'src/api/Installation/InstallationService';

export const useInstallationStore = defineStore('counter', () => {
  const installations = ref<InstallationModel[]>([]);
  const currentInstallation = ref<InstallationModel>();

  async function fetchInstallations() {
    const data = await useInstallationService().getInstallations();

    installations.value = data;
    if (!currentInstallation.value) {
      currentInstallation.value = data.at(0);
    }
  }

  return {
    installations,
    currentInstallation,
    fetchInstallations,
  };
});
