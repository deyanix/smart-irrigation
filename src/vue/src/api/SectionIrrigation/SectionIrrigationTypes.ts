import { SerializerDeclaration } from 'src/utilities/SerializerUtilities';

export interface SectionIrrigationModel {
  start: Date;
  end: Date;
}

export const SerializerIrrigationDeclaration: SerializerDeclaration<SectionIrrigationModel> =
  [
    { path: 'start', type: 'date' },
    { path: 'end', type: 'date' },
  ];
