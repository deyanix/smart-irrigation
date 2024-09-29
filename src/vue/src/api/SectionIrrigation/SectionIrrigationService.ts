import {
  IrrigationModel,
  IrrigationSearchRequest,
  SectionIrrigationModel,
  SerializerIrrigationDeclaration,
  SerializerIrrigationSearchRequestDeclaration,
} from 'src/api/SectionIrrigation/SectionIrrigationTypes';
import { api } from 'boot/axios';
import { SerializerUtilities } from 'src/utilities/SerializerUtilities';
import { AppSearchResponse } from 'src/utilities/ApiUtilities';

export const SectionIrrigationService = {
  async search(
    sectionId: number,
    data: IrrigationSearchRequest
  ): Promise<AppSearchResponse<IrrigationModel>> {
    const response = await api.get(
      `/installations/any/sections/${sectionId}/irrigations`,
      {
        params: SerializerUtilities.serialize(
          data,
          SerializerIrrigationSearchRequestDeclaration
        ),
      }
    );

    return SerializerUtilities.deserializeSearch<IrrigationModel>(
      response.data,
      SerializerIrrigationDeclaration
    );
  },
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
