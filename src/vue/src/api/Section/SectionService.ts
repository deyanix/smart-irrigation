import { SectionModel } from './SectionTypes';
import { api } from 'boot/axios';

export const SectionService = {
  async getSections(installationId: number): Promise<SectionModel[]> {
    const response = await api.get(`/installations/${installationId}/sections`);
    return response.data;
  },
};
