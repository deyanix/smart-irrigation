import { SerializerDeclaration } from 'src/utilities/SerializerUtilities';

export interface SensorItemModel {
  id: number;
  name: string;
  measurementUnitId: number;
  measurementUnitName: string;
  measurementUnitSymbol: string;
  lastMeasurementDate: Date;
  lastMeasurementValue: number;
}

export const SensorItemDeclaration: SerializerDeclaration<SensorItemModel> = [
  { path: 'lastMeasurementDate', type: 'datetime' },
];

export interface SensorModel {
  id: number;
  name: string;
  typeId: number;
  typeName: string;
  items: SensorItemModel[];
}

export const SensorDeclaration: SerializerDeclaration<SensorModel> = [
  { path: 'items', type: 'array', declaration: SensorItemDeclaration },
];
