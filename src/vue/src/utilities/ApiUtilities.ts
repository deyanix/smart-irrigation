export interface AppSearchRequest {
  page?: number;
  pageSize?: number;
}

export interface AppSearchResponse<T> {
  rows: T[];
  totalRows: number;
}

export const ApiUtilities = {};
