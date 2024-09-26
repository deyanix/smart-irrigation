import { Duration } from 'dayjs/plugin/duration';
import dayjs from 'dayjs';

export interface DurationOption {
  label: string;
  value: string;
  duration: Duration;
}

export const startFromOptions: DurationOption[] = [
  { label: 'Teraz', value: 'now', duration: dayjs.duration({ minutes: 0 }) },
  {
    label: 'Za 30 minut',
    value: '30min',
    duration: dayjs.duration({ minutes: 30 }),
  },
  {
    label: 'Za 1 godzinę',
    value: '1h',
    duration: dayjs.duration({ hours: 1 }),
  },
  {
    label: 'Za 3 godziny',
    value: '3h',
    duration: dayjs.duration({ hours: 3 }),
  },
];

export const startToOptions: DurationOption[] = [
  { label: '5 minut', value: '5min', duration: dayjs.duration({ minutes: 5 }) },
  {
    label: '10 minut',
    value: '10min',
    duration: dayjs.duration({ minutes: 10 }),
  },
  {
    label: '15 minut',
    value: '15min',
    duration: dayjs.duration({ minutes: 15 }),
  },
  {
    label: '20 minut',
    value: '20min',
    duration: dayjs.duration({ minutes: 20 }),
  },
  {
    label: '30 minut',
    value: '30min',
    duration: dayjs.duration({ minutes: 30 }),
  },
];
