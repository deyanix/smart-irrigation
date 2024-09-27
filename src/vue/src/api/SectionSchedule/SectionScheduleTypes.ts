import { AppSearchRequest } from 'src/utilities/ApiUtilities';
import { SerializerDeclaration } from 'src/utilities/SerializerUtilities';

export interface SectionScheduleSearchRequest extends AppSearchRequest {
  from?: Date;
  to?: Date;
}

export const SectionScheduleSearchRequestDeclaration: SerializerDeclaration<SectionScheduleSearchRequest> =
  [
    { path: 'from', type: 'datetime' },
    { path: 'to', type: 'datetime' },
  ];

export interface SectionScheduleModel extends SectionScheduleUpdate {
  id: number;
}

export const SectionScheduleDeclaration: SerializerDeclaration<SectionScheduleUpdate> =
  [
    { path: 'start', type: 'datetime' },
    { path: 'end', type: 'datetime' },
  ];

export interface SectionScheduleUpdate {
  start?: Date;
  end?: Date;
  state: boolean;
}
