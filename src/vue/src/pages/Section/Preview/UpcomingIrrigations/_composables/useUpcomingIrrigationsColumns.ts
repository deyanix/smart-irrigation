import { QTableColumn } from 'quasar';
import { FormationUtilities } from 'src/utilities/FormationUtilities';

export function useUpcomingIrrigationsColumns(): QTableColumn[] {
  return [
    {
      name: 'startDate',
      field: 'start',
      label: 'Data',
      format: (val) => FormationUtilities.formatDate(val) ?? '',
      align: 'left',
    },
    {
      name: 'startTime',
      field: 'start',
      label: 'Od',
      format: (val) => FormationUtilities.formatTime(val) ?? '',
      align: 'left',
    },
    {
      name: 'endTime',
      field: 'end',
      label: 'Do',
      format: (val) => FormationUtilities.formatTime(val) ?? '',
      align: 'left',
    },
    {
      name: 'actions',
      field: 'actions',
      label: '',
      align: 'right',
    },
  ];
}
