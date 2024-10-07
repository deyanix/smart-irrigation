import {
  MeasurementCriteria,
  MeasurementCriteriaDeclaration,
  MeasurementDeclaration,
  MeasurementModel,
} from 'src/api/Measurement/MeasurementTypes';
import { api } from 'boot/axios';
import { SerializerUtilities } from 'src/utilities/SerializerUtilities';

export const MeasurementService = {
  async getMeasurements(
    sensorItemId: number,
    criteria: MeasurementCriteria
  ): Promise<MeasurementModel[]> {
    const response = await api.get(
      `/sensors/any/items/${sensorItemId}/measurements`,
      {
        params: SerializerUtilities.serialize(
          criteria,
          MeasurementCriteriaDeclaration
        ),
      }
    );

    return SerializerUtilities.deserializeArray(
      response.data,
      MeasurementDeclaration
    );
  },
};
