import { defineStore } from 'pinia';
import { ref } from 'vue';
import { InstallationModel } from 'src/api/Installation/InstallationTypes';
import { InstallationService } from 'src/api/Installation/InstallationService';

export const useInstallationStore = defineStore('counter', () => {
  const installations = ref<InstallationModel[]>([]);
  const currentInstallation = ref<InstallationModel>();

  async function fetchInstallations() {
    const data = await InstallationService.getInstallations();

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
