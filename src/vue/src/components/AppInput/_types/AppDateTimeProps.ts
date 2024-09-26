import { QInputProps } from 'quasar';
import { AppDateProps } from 'components/AppInput/_types/AppDateProps';
import { AppDateTimeMode } from 'components/AppInput/_types/AppDateTimeMode';

export interface AppDateTimeProps
  extends Omit<AppDateProps, 'format'>,
    Omit<QInputProps, 'modelValue' | 'onUpdate:modelValue'> {
  mode?: AppDateTimeMode;
}

export const AppDateTimeDefaultProps = {
  mode: 'datetime',
  rules: () => [],
};
