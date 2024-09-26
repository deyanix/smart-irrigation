import { SectionModel } from 'src/api/Section/SectionTypes';
import { api } from 'boot/axios';
import { useInstallationId } from 'src/composables/useInstallationId';

export function useSectionService() {
  const installationId = useInstallationId();

  return {
    async getSections(): Promise<SectionModel[]> {
      const response = await api.get(
        `/installations/${installationId.value}/sections`
      );
      return response.data;
    },
  };
}
