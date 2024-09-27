import { api } from 'boot/axios';
import {
  SectionScheduleDeclaration,
  SectionScheduleModel,
  SectionScheduleSearchRequest,
  SectionScheduleSearchRequestDeclaration,
  SectionScheduleUpdate,
} from './SectionScheduleTypes';
import { AppSearchResponse } from 'src/utilities/ApiUtilities';
import { SerializerUtilities } from 'src/utilities/SerializerUtilities';

export const SectionScheduleService = {
  async search(
    sectionId: number,
    data: SectionScheduleSearchRequest
  ): Promise<AppSearchResponse<SectionScheduleModel>> {
    const response = await api.get(
      `/installations/any/sections/${sectionId}/schedules`,
      {
        params: SerializerUtilities.serialize(
          data,
          SectionScheduleSearchRequestDeclaration
        ),
      }
    );
    return SerializerUtilities.deserializeSearch<SectionScheduleModel>(
      response.data,
      SectionScheduleDeclaration
    );
  },
  async create(sectionId: number, data: SectionScheduleUpdate) {
    await api.post(
      `/installations/any/sections/${sectionId}/schedules`,
      SerializerUtilities.serialize(data, SectionScheduleDeclaration)
    );
  },
};
