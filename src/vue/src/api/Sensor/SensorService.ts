import { api } from 'boot/axios';

export const SensorService = {
  async getSensors(installationId: number) {
    const response = await api.get(`/installations/${installationId}/sensors`);
    return response.data;
  },
};
