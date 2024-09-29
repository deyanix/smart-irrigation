import { AppSearchRequest } from 'src/utilities/ApiUtilities';
import { QTableProps } from 'quasar';
import { isNumber } from 'radashi';

export type QTablePagination = Exclude<QTableProps['pagination'], undefined>;
export type QTableRequestEventParameters = Parameters<
  Exclude<QTableProps['onRequest'], undefined>
>;
export type QTableRequestEvent = QTableRequestEventParameters[0];

export const QuasarUtilities = {
  assignRequestFromPagination(
    request: AppSearchRequest,
    pagination: QTablePagination | undefined | null
  ) {
    if (!pagination) return;
    request.page = isNumber(pagination.page) ? pagination.page - 1 : undefined;
    request.pageSize = pagination.rowsPerPage;
  },
  assignPaginationFromRequest(
    pagination: QTablePagination,
    request: AppSearchRequest
  ) {
    pagination.page = isNumber(request.page) ? request.page + 1 : undefined;
    pagination.rowsPerPage = request.pageSize;
  },
};
