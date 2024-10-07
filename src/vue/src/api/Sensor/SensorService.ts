import { api } from 'boot/axios';
import { SensorDeclaration, SensorModel } from 'src/api/Sensor/SensorTypes';
import { SerializerUtilities } from 'src/utilities/SerializerUtilities';

export const SensorService = {
  async getSensors(installationId: number): Promise<SensorModel[]> {
    const response = await api.get(`/installations/${installationId}/sensors`);
    return SerializerUtilities.deserializeArray(
      response.data,
      SensorDeclaration
    );
  },
};
