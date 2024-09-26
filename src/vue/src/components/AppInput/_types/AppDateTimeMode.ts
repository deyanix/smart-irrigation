export interface AppDateTimeModeDefinition {
  format: string;
  inputMask: string;
  showCalendar: boolean;
  showClock: boolean;
}

export type AppDateTimeMode = keyof typeof AppDateTimeModes;

export const AppDateTimeModes: Record<string, AppDateTimeModeDefinition> = {
  datetime: {
    format: 'YYYY-MM-DD HH:mm',
    inputMask: '####-##-## ##:##',
    showCalendar: true,
    showClock: true,
  },
  date: {
    format: 'YYYY-MM-DD',
    inputMask: '####-##-##',
    showCalendar: true,
    showClock: false,
  },
  time: {
    format: 'HH:mm',
    inputMask: '##:##',
    showCalendar: false,
    showClock: true,
  },
};
