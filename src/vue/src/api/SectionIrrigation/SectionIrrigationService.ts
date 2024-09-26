import {
  SectionIrrigationModel,
  SerializerIrrigationDeclaration,
} from 'src/api/SectionIrrigation/SectionIrrigationTypes';
import { api } from 'boot/axios';
import { SerializerUtilities } from 'src/utilities/SerializerUtilities';

export const SectionIrrigationService = {
  async getUpcomingIrrigations(
    sectionId: number
  ): Promise<SectionIrrigationModel[]> {
    const response = await api.get(
      `/installations/any/sections/${sectionId}/irrigations/upcoming`
    );

    return SerializerUtilities.deserializeArray(
      response.data,
      SerializerIrrigationDeclaration
    );
  },
};
