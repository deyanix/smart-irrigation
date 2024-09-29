import dayjs from 'dayjs';

export interface DurationOption {
  label: string;
  value: string;
  mutation: (date: dayjs.Dayjs) => Date;
}

export const startFromOptions: DurationOption[] = [
  {
    label: 'Teraz',
    value: 'now',
    mutation: (date) => date.toDate(),
  },
  {
    label: 'Za 30 minut',
    value: '30min',
    mutation: (date) => date.add(30, 'minutes').toDate(),
  },
  {
    label: 'Za 1 godzinę',
    value: '1h',
    mutation: (date) => date.add(1, 'hours').toDate(),
  },
  {
    label: 'Za 3 godziny',
    value: '3h',
    mutation: (date) => date.add(3, 'hours').toDate(),
  },
];

export const startToOptions: DurationOption[] = [
  {
    label: '5 minut',
    value: '5min',
    mutation: (date) => date.add(5, 'minutes').toDate(),
  },
  {
    label: '10 minut',
    value: '10min',
    mutation: (date) => date.add(10, 'minutes').toDate(),
  },
  {
    label: '15 minut',
    value: '15min',
    mutation: (date) => date.add(15, 'minutes').toDate(),
  },
  {
    label: '20 minut',
    value: '20min',
    mutation: (date) => date.add(20, 'minutes').toDate(),
  },
  {
    label: '30 minut',
    value: '30min',
    mutation: (date) => date.add(30, 'minutes').toDate(),
  },
];

export const pauseFromOptions: DurationOption[] = [
  {
    label: 'Teraz',
    value: 'now',
    mutation: (date) => date.toDate(),
  },
  {
    label: 'Od jutra',
    value: 'tomorrow',
    mutation: (date) => date.add(1, 'days').startOf('date').toDate(),
  },
  {
    label: 'Od pojutrza',
    value: '2days',
    mutation: (date) => date.add(2, 'days').startOf('date').toDate(),
  },
];

export const pauseToOptions: DurationOption[] = [
  {
    label: 'Do końca dnia',
    value: 'end-day',
    mutation: (date) => date.add(1, 'days').startOf('date').toDate(),
  },
  {
    label: 'Do końca kolejnego dnia',
    value: 'end-tomorrow',
    mutation: (date) => date.add(2, 'days').startOf('date').toDate(),
  },
  {
    label: 'Do końca tygodnia',
    value: 'end-week',
    mutation: (date) => date.add(1, 'days').weekday(7).startOf('date').toDate(),
  },
];
