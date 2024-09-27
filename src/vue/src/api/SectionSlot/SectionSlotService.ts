import { api } from 'boot/axios';
import { SerializerUtilities } from 'src/utilities/SerializerUtilities';
import {
  SectionSlotModel,
  SectionSlotUpdate,
  SectionSlotDeclaration,
} from './SectionSlotTypes';
import { DayOfWeeks } from 'src/utilities/_types/DayOfWeeks';
import { isNumber } from 'radashi';

export const SectionSlotService = {
  createEmptyUpdate(): SectionSlotUpdate {
    return {
      weekdays: DayOfWeeks.map((d) => d.nativeValue),
    };
  },
  isModel(data: SectionSlotUpdate): data is SectionSlotModel {
    return 'id' in data && isNumber(data.id);
  },
  async getSlots(sectionId: number): Promise<SectionSlotModel[]> {
    const response = await api.get(
      `/installations/any/sections/${sectionId}/slots`
    );
    return SerializerUtilities.deserializeArray<SectionSlotModel>(
      response.data,
      SectionSlotDeclaration
    );
  },
  async create(sectionId: number, request: SectionSlotUpdate): Promise<void> {
    await api.post(
      `/installations/any/sections/${sectionId}/slots`,
      SerializerUtilities.serialize<SectionSlotUpdate>(
        request,
        SectionSlotDeclaration
      )
    );
  },
  async update(slotId: number, request: SectionSlotUpdate): Promise<void> {
    await api.put(
      `/installations/any/sections/any/slots/${slotId}`,
      SerializerUtilities.serialize<SectionSlotUpdate>(
        request,
        SectionSlotDeclaration
      )
    );
  },
  async delete(slotId: number): Promise<void> {
    await api.delete(`/installations/any/sections/any/slots/${slotId}`);
  },
};
