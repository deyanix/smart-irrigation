import { QTableColumn } from 'quasar';
import { FormationUtilities } from 'src/utilities/FormationUtilities';

export function useSectionSchedulesColumns(): QTableColumn[] {
  return [
    {
      name: 'state',
      field: 'state',
      label: '',
      align: 'left',
      style: 'width: 48px',
    },
    {
      name: 'start',
      field: 'start',
      label: 'Od',
      format: (val) => FormationUtilities.formatDateTime(val) ?? '',
      align: 'left',
    },
    {
      name: 'end',
      field: 'end',
      label: 'Do',
      format: (val) => FormationUtilities.formatDateTime(val) ?? '',
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
