import { SerializerDeclaration } from 'src/utilities/SerializerUtilities';
import { AppSearchRequest } from 'src/utilities/ApiUtilities';

export interface IrrigationModel {
  id: number;
  start: Date;
  end: Date;
  finished: boolean;
}

export interface IrrigationSearchRequest extends AppSearchRequest {
  from?: Date;
  to?: Date;
}

export const SerializerIrrigationSearchRequestDeclaration: SerializerDeclaration<IrrigationSearchRequest> =
  [
    { path: 'from', type: 'datetime' },
    { path: 'to', type: 'datetime' },
  ];

export interface SectionIrrigationModel {
  start: Date;
  end: Date;
}

export const SerializerIrrigationDeclaration: SerializerDeclaration<SectionIrrigationModel> =
  [
    { path: 'start', type: 'date' },
    { path: 'end', type: 'date' },
  ];
