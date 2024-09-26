import { SerializerDeclaration } from 'src/utilities/SerializerUtilities';

export interface SectionSlotModel extends SectionSlotUpdate {
  id: number;
  start: Date;
  end: Date;
}

export interface SectionSlotUpdate {
  start?: Date;
  end?: Date;
  weekdays: number[];
}

export const SectionSlotDeclaration: SerializerDeclaration<SectionSlotUpdate> =
  [
    { path: 'start', type: 'time' },
    { path: 'end', type: 'time' },
    { path: 'weekdays', type: 'weekdays' },
  ];
