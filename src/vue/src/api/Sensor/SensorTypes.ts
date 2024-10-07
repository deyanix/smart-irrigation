export interface SensorModel {
  id: number;
  name: string;
  typeId: number;
  typeName: string;
  items: SensorItemModel[];
}

export interface SensorItemModel {
  id: number;
  name: string;
  measurementUnitId: number;
  measurementUnitName: string;
  measurementUnitSymbol: string;
}
