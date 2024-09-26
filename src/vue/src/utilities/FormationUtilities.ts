import { isDate, isString } from 'radashi';
import { DayOfWeeks } from 'src/utilities/_types/DayOfWeeks';
import dayjs from 'dayjs';

function formatDateWrapper(
  date: unknown,
  cb: (wrapper: dayjs.Dayjs) => string | undefined
) {
  if (!isDate(date) && !isString(date)) {
    return undefined;
  }

  const wrapper = dayjs(date);
  if (!wrapper.isValid()) {
    return undefined;
  }
  return cb(wrapper);
}

export const FormationUtilities = {
  formatDate(date: unknown): string | undefined {
    return this.formatDateCustom(date, 'YYYY-MM-DD');
  },
  formatDateTime(date: unknown): string | undefined {
    return this.formatDateCustom(date, 'YYYY-MM-DD HH:mm');
  },
  formatTime(date: unknown): string | undefined {
    return this.formatDateCustom(date, 'HH:mm');
  },
  formatDateCustom(date: unknown, format: string): string | undefined {
    return formatDateWrapper(date, (wrapper) => wrapper.format(format));
  },
  formatDateFromNow(date: unknown): string | undefined {
    return formatDateWrapper(date, (wrapper) => {
      const diff = wrapper.startOf('date').diff(dayjs().startOf('date'));
      const diffDays = dayjs.duration(diff).asDays();
      if (diffDays === 0) {
        return 'dziś';
      } else if (diffDays == 1) {
        return 'jutro';
      } else if (diffDays == 2) {
        return 'pojutrze';
      } else if (diffDays == -1) {
        return 'wczoraj';
      } else if (diffDays == -2) {
        return 'przedwczoraj';
      } else if (diffDays > 2) {
        return `za ${diffDays} dni`;
      } else if (diffDays < 2) {
        return `${diffDays} dni temu`;
      } else {
        return undefined;
      }
    });
  },
  formatWeekdays(weekdays: number[]): string {
    const dayOfWeeks = DayOfWeeks.filter((d) =>
      weekdays.includes(d.nativeValue)
    );

    if (dayOfWeeks.length === DayOfWeeks.length) {
      return 'codziennie';
    }

    return dayOfWeeks.map((d) => d.shortLabel).join(', ');
  },
};
