import { QTableColumn } from 'quasar';

export function useSectionColumns(): QTableColumn[] {
  return [
    {
      name: 'status',
      field: '',
      label: '',
      align: 'center',
      style: 'width: 32px; vertical-align: middle;',
    },
    {
      name: 'name',
      field: 'name',
      label: 'Nazwa',
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
