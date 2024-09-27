import dayjs from 'dayjs';

export const DateUtilities = {
  getDateWithTime(date: Date, time: Date) {
    const timeWrapper = dayjs(time);
    return dayjs(date)
      .startOf('day')
      .add(timeWrapper.hour(), 'hour')
      .add(timeWrapper.minute(), 'minute')
      .toDate();
  },
};
