import { InstallationModel } from 'src/api/Installation/InstallationTypes';
import { api } from 'boot/axios';

export function useInstallationService() {
  return {
    async getInstallations(): Promise<InstallationModel[]> {
      const response = await api.get('/installations');
      return response.data;
    },
  };
}
