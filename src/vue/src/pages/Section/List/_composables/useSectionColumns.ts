import { QTableColumn } from 'quasar';

export function useSectionColumns(): QTableColumn[] {
  return [
    {
      name: 'name',
      field: 'name',
      label: 'Nazwa',
      align: 'left',
    },
    {
      name: 'control',
      field: 'control',
      label: '',
      align: 'right',
    },
    {
      name: 'actions',
      field: 'actions',
      label: '',
      align: 'right',
    },
  ];
}
