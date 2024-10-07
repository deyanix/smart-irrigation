import { QTableColumn } from 'quasar';
import { SensorModel } from 'src/api/Sensor';
import { FormationUtilities } from 'src/utilities/FormationUtilities';
import { isDate } from 'radashi';

export function useSensorColumns(): QTableColumn<SensorModel, string>[] {
  return [
    {
      name: 'name',
      field: 'name',
      label: 'Nazwa',
      align: 'left',
    },
    {
      name: 'lastMeasurementDate',
      field: 'lastMeasurement',
      label: 'Ostatni pomiar',
      align: 'left',
      format: (_, row) => {
        const lastMeasurementDate = row.items
          .map((item) => item.lastMeasurementDate)
          .reduce((previous, current) => {
            if (isDate(previous) && isDate(current)) {
              return previous > current ? previous : current;
            } else if (isDate(previous)) {
              return previous;
            } else if (isDate(current)) {
              return current;
            } else {
              return null;
            }
          }, null);

        return (
          FormationUtilities.formatDateTime(lastMeasurementDate) ?? '(brak)'
        );
      },
    },
    {
      name: 'actions',
      field: 'actions',
      label: '',
      align: 'right',
    },
  ];
}
