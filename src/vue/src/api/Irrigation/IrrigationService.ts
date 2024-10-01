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
  async searchBySection(
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
  async searchByInstallation(
    installationId: number,
    data: IrrigationSearchRequest
  ): Promise<AppSearchResponse<IrrigationModel>> {
    const response = await api.get(
      `/installations/${installationId}/sections/any/irrigations`,
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
  async getUpcomingIrrigationsBySection(
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
  async getUpcomingIrrigationsByInstallation(
    installationId: number
  ): Promise<UpcomingIrrigationModel[]> {
    const response = await api.get(
      `/installations/${installationId}/sections/any/irrigations/upcoming`
    );

    return SerializerUtilities.deserializeArray(
      response.data,
      UpcomingIrrigationDeclaration
    );
  },
};
