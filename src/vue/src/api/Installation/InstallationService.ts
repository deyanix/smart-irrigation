import { InstallationModel } from './InstallationTypes';
import { api } from 'boot/axios';

export const InstallationService = {
  async getInstallations(): Promise<InstallationModel[]> {
    const response = await api.get('/installations');
    return response.data;
  },
};
