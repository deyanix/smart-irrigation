import { SerializerDeclaration } from 'src/utilities/SerializerUtilities';

export interface IrrigationModel {
  id: number;
  start: Date;
  end: Date;
  finished: boolean;
}

export interface SectionIrrigationModel {
  start: Date;
  end: Date;
}

export const SerializerIrrigationDeclaration: SerializerDeclaration<SectionIrrigationModel> =
  [
    { path: 'start', type: 'date' },
    { path: 'end', type: 'date' },
  ];
