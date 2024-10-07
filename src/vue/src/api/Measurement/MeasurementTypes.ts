import { SerializerDeclaration } from 'src/utilities/SerializerUtilities';

export interface MeasurementCriteria {
  dateFrom?: Date;
  dateTo?: Date;
}

export const MeasurementCriteriaDeclaration: SerializerDeclaration<MeasurementCriteria> =
  [
    { path: 'dateFrom', type: 'datetime' },
    { path: 'dateTo', type: 'datetime' },
  ];

export interface MeasurementModel {
  date: Date;
  value: number | null;
}

export const MeasurementDeclaration: SerializerDeclaration<MeasurementModel> = [
  { path: 'date', type: 'datetime' },
];
