import dayjs from 'dayjs';
import { clone, get, isArray, isEmpty, isString, set } from 'radashi';
import { mapToDayOfWeeks, mapToNativeDayOfWeeks } from './_types/DayOfWeeks';
import { AppSearchResponse } from 'src/utilities/ApiUtilities';

export type SerializerFieldType =
  | 'date'
  | 'datetime'
  | 'time'
  | 'weekdays'
  | 'object'
  | 'array';

export type SerializerField<Data, NestedData = any> = {
  path: keyof Data | string;
  type: SerializerFieldType;
  declaration?: SerializerDeclaration<NestedData>;
};

export type SerializerDeclaration<Data> = SerializerField<Data>[];

export const SerializerUtilities = {
  deserialize<Data extends object>(
    data: Data,
    declaration: SerializerDeclaration<Data>
  ): Data {
    let result = clone(data);
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
        case 'object':
          if (field.declaration) {
            result = set(
              result,
              path,
              this.deserialize(result, field.declaration)
            );
          }
          break;
        case 'array':
          if (field.declaration && isArray(value)) {
            result = set(
              result,
              path,
              this.deserializeArray(value, field.declaration)
            );
          }
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
  deserializeSearch<Data extends object>(
    data: AppSearchResponse<Data>,
    declaration: SerializerDeclaration<Data>
  ): AppSearchResponse<Data> {
    return {
      ...data,
      rows: this.deserializeArray(data.rows, declaration),
    };
  },
  serialize<Data extends object>(
    data: Data,
    declaration: SerializerDeclaration<Data>
  ): Data {
    let result = clone(data);
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
              dayjs(value).format('YYYY-MM-DDTHH:mm:ss')
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
        case 'object':
          if (field.declaration) {
            result = set(
              result,
              path,
              this.serialize(result, field.declaration)
            );
          }
          break;
        case 'array':
          if (field.declaration && isArray(value)) {
            result = set(
              result,
              path,
              this.serializeArray(value, field.declaration)
            );
          }
          break;
      }
    });

    return result;
  },
  serializeArray<Data extends object>(
    data: Data[],
    declaration: SerializerDeclaration<Data>
  ): Data[] {
    return data.map((el) => this.serialize(el, declaration));
  },
};
