import {
  AppSearchRequest,
  AppSearchResponse,
} from 'src/utilities/ApiUtilities';
import { ref, watch } from 'vue';
import {
  QTablePagination,
  QTableRequestEvent,
  QuasarUtilities,
} from 'src/utilities/QuasarUtilities';

export function useSearchTable<Request extends AppSearchRequest, Row>(
  provider: (request: Request) => Promise<AppSearchResponse<Row>>,
  defaultRequest: Request
) {
  const loading = ref<boolean>(false);
  const rows = ref<Row[]>([]);
  const pagination = ref<QTablePagination>({});
  const request = ref<Request>({
    ...defaultRequest,
    page: 0,
    pageSize: 10,
  });

  async function fetchTable(props?: QTableRequestEvent): Promise<void> {
    loading.value = true;
    try {
      QuasarUtilities.assignRequestFromPagination(
        request.value,
        props?.pagination
      );
      const response = await provider(request.value);

      rows.value = response.rows;
      pagination.value.rowsNumber = response.totalRows;
    } finally {
      loading.value = false;
    }
  }

  watch(
    request,
    () =>
      QuasarUtilities.assignPaginationFromRequest(
        pagination.value,
        request.value
      ),
    { deep: true, immediate: true }
  );

  return { loading, rows, pagination, request, fetchTable };
}
