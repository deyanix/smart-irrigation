import { MaybeRefOrGetter, toValue } from 'vue';
import { AppDateProps } from 'components/AppInput/_types/AppDateProps';
import dayjs from 'dayjs';
import { ValidationRule } from 'quasar';

export function useDateValidator(propsRef: MaybeRefOrGetter<AppDateProps>) {
  return (d: Date) => {
    const props = toValue(propsRef);
    const now = dayjs();
    const date = dayjs(d);

    return (
      date.isValid() &&
      (!props.dateFrom ||
        date.isAfter(props.dateFrom) ||
        (!props.exclusiveRules && date.isSame(props.dateFrom, 'day'))) &&
      (!props.dateFromNow ||
        date.isAfter(now) ||
        (!props.exclusiveRules && date.isSame(now, 'day'))) &&
      (!props.dateTo ||
        date.isBefore(props.dateTo) ||
        (!props.exclusiveRules && date.isSame(props.dateTo, 'day'))) &&
      (!props.dateToNow ||
        date.isBefore(now) ||
        (!props.exclusiveRules && date.isSame(now, 'day')))
    );
  };
}

export function dateRule(
  propsRef: MaybeRefOrGetter<AppDateProps>,
  formatRef?: MaybeRefOrGetter<string>
): ValidationRule {
  const validateDate = useDateValidator(propsRef);

  return (v: string) => {
    const props = toValue(propsRef);
    const format = toValue(formatRef) ?? props.format;
    return !v || validateDate(dayjs(v, format).toDate());
  };
}
