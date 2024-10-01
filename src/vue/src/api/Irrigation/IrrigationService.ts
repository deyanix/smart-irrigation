import {
  IrrigationModel,
  IrrigationSearchRequest,
  UpcomingIrrigationModel,
  UpcomingIrrigationDeclaration,
  SerializerIrrigationSearchRequestDeclaration,
  SerializerIrrigationDeclaration,
} from 'src/api/Irrigation/IrrigationTypes';
import { api } from 'boot/axios';
import { SerializerUtilities } from 'src/utilities/SerializerUtilities';
import { AppSearchResponse } from 'src/utilities/ApiUtilities';

export const IrrigationService = {
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
  ): Promise<UpcomingIrrigationModel[]> {
    const response = await api.get(
      `/installations/any/sections/${sectionId}/irrigations/upcoming`
    );

    return SerializerUtilities.deserializeArray(
      response.data,
      UpcomingIrrigationDeclaration
    );
  },
};
