import { api } from 'boot/axios';
import { SectionScheduleUpdate } from './SectionScheduleTypes';

export const SectionScheduleService = {
  async create(sectionId: number, data: SectionScheduleUpdate) {
    await api.post(`/installations/any/sections/${sectionId}/schedules`, data);
  },
};
