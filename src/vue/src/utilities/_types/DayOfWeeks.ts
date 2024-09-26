import { isArray, unique } from 'radashi';

export interface DayOfWeek {
  label: string;
  shortLabel: string;
  value: number;
  nativeValue: number;
}

export const DayOfWeeks = [
  { label: 'Poniedziałek', shortLabel: 'po', value: 1, nativeValue: 1 },
  { label: 'Wtorek', shortLabel: 'wt', value: 2, nativeValue: 2 },
  { label: 'Środa', shortLabel: 'śr', value: 3, nativeValue: 3 },
  { label: 'Czwartek', shortLabel: 'cz', value: 4, nativeValue: 4 },
  { label: 'Piątek', shortLabel: 'pt', value: 5, nativeValue: 5 },
  { label: 'Sobota', shortLabel: 'sb', value: 6, nativeValue: 6 },
  { label: 'Niedziela', shortLabel: 'nd', value: 7, nativeValue: 0 },
];

export function getDayOfWeek(value: unknown): DayOfWeek | undefined {
  return DayOfWeeks.find((d) => d.value === value);
}

export function getDayOfWeekByNative(
  nativeValue: unknown
): DayOfWeek | undefined {
  return DayOfWeeks.find((d) => d.nativeValue === nativeValue);
}

export function mapToDayOfWeeks(values: unknown): number[] {
  if (isArray(values)) {
    return unique(
      values
        .map((v) => getDayOfWeekByNative(v)?.value)
        .filter((w) => w !== undefined)
    );
  }
  return [];
}

export function mapToNativeDayOfWeeks(values: unknown): number[] {
  if (isArray(values)) {
    return unique(
      values
        .map((v) => getDayOfWeek(v)?.nativeValue)
        .filter((w) => w !== undefined)
    );
  }
  return [];
}
