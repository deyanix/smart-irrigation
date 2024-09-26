import { SectionScheduleUpdate } from 'src/api/SectionSchedule/index';
import { api } from 'boot/axios';
import { useInstallationId } from 'src/composables/useInstallationId';

export function useSectionScheduleService() {
  const installationId = useInstallationId();

  return {
    async create(sectionIndex: number, data: SectionScheduleUpdate) {
      await api.post(
        `/installations/${installationId.value}/sections/${sectionIndex}/schedules`,
        data
      );
    },
  };
}
