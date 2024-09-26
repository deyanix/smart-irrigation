import dayjs from 'dayjs';
import { get, isEmpty, isString, set } from 'radashi';
import { mapToDayOfWeeks, mapToNativeDayOfWeeks } from './_types/DayOfWeeks';

export type SerializerFieldType = 'date' | 'datetime' | 'time' | 'weekdays';

export type SerializerField<Data> = {
  path: keyof Data | string;
  type: SerializerFieldType;
};

export type SerializerDeclaration<Data> = SerializerField<Data>[];

export const SerializerUtilities = {
  deserialize<Data extends object>(
    data: Data,
    declaration: SerializerDeclaration<Data>
  ): Data {
    let result = { ...data };
    declaration.forEach((field) => {
      const path = field.path.toString();
      const value = get(result, path);
      if (isEmpty(value)) {
        return;
      }

      switch (field.type) {
        case 'datetime':
        case 'date':
          if (isString(value)) {
            result = set(result, path, dayjs(value).toDate());
          }
          break;
        case 'time':
          if (isString(value)) {
            result = set(result, path, dayjs(value, 'HH:mm:ss').toDate());
          }
          break;
        case 'weekdays':
          result = set(result, path, mapToNativeDayOfWeeks(value));
          break;
      }
    });

    return result;
  },
  deserializeArray<Data extends object>(
    data: Data[],
    declaration: SerializerDeclaration<Data>
  ): Data[] {
    return data.map((el) => this.deserialize(el, declaration));
  },
  serialize<Data extends object>(
    data: Data,
    declaration: SerializerDeclaration<Data>
  ): Data {
    let result = { ...data };
    declaration.forEach((field) => {
      const path = field.path.toString();
      const value = get(result, path);
      if (isEmpty(value)) {
        return;
      }

      switch (field.type) {
        case 'datetime':
          if (value instanceof Date) {
            result = set(
              result,
              path,
              dayjs(value).format("YYYY-MM-DD'T'HH:mm.SSS'Z'")
            );
          }
          break;
        case 'date':
          if (value instanceof Date) {
            result = set(
              result,
              path,
              dayjs(value).startOf('day').format('YYYY-MM-DD')
            );
          }
          break;
        case 'time':
          if (value instanceof Date) {
            result = set(result, path, dayjs(value).format('HH:mm:ss.SSS'));
          }
          break;
        case 'weekdays':
          result = set(result, path, mapToDayOfWeeks(value));
          break;
      }
    });

    return result;
  },
};
