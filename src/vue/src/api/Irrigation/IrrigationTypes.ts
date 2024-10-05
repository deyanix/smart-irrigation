import { SerializerDeclaration } from 'src/utilities/SerializerUtilities';
import { AppSearchRequest } from 'src/utilities/ApiUtilities';

export interface IrrigationModel {
  id: number;
  start: Date;
  end: Date;
  finished: boolean;
  sectionId: number;
  sectionName: string;
}

export const SerializerIrrigationDeclaration: SerializerDeclaration<IrrigationModel> =
  [
    { path: 'from', type: 'datetime' },
    { path: 'to', type: 'datetime' },
  ];

export interface IrrigationSearchRequest extends AppSearchRequest {
  from?: Date;
  to?: Date;
}

export const SerializerIrrigationSearchRequestDeclaration: SerializerDeclaration<IrrigationSearchRequest> =
  [
    { path: 'from', type: 'datetime' },
    { path: 'to', type: 'datetime' },
  ];

export interface UpcomingIrrigationSource {
  start: Date;
  end: Date;
  slotId: number | null;
  scheduleId: number | null;
  state: boolean;
}

export const UpcomingIrrigationSourceDeclaration: SerializerDeclaration<UpcomingIrrigationSource> =
  [
    { path: 'start', type: 'date' },
    { path: 'end', type: 'date' },
  ];

export interface UpcomingIrrigationModel {
  start: Date;
  end: Date;
  sectionId: number;
  sectionName: string;
  sources: UpcomingIrrigationSource[];
}

export const UpcomingIrrigationDeclaration: SerializerDeclaration<UpcomingIrrigationModel> =
  [
    { path: 'start', type: 'date' },
    { path: 'end', type: 'date' },
    {
      path: 'sources',
      type: 'array',
      declaration: UpcomingIrrigationSourceDeclaration,
    },
  ];
