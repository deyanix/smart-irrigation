import { SectionModel } from './SectionTypes';
import { api } from 'boot/axios';

export const SectionService = {
  async getSections(installationId: number): Promise<SectionModel[]> {
    const response = await api.get(`/installations/${installationId}/sections`);
    return response.data;
  },
  async getSection(sectionId: number): Promise<SectionModel> {
    const response = await api.get(`/installations/any/sections/${sectionId}`);
    return response.data;
  },
  async stop(sectionId: number): Promise<void> {
    await api.post(`/installations/any/sections/${sectionId}/stop`);
  },
};
