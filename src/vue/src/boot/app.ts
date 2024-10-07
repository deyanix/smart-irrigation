import { boot } from 'quasar/wrappers';
import { useInstallationStore } from 'stores/installation';
import dayjs from 'dayjs';
import Duration from 'dayjs/plugin/duration';
import CustomParseFormat from 'dayjs/plugin/customParseFormat';
import RelativeTime from 'dayjs/plugin/relativeTime';
import Weekday from 'dayjs/plugin/weekday';
import 'dayjs/locale/pl';
import VueApexCharts from 'vue3-apexcharts';
import { get } from 'radashi';

export default boot(async ({ app }) => {
  dayjs.extend(Duration);
  dayjs.extend(CustomParseFormat);
  dayjs.extend(RelativeTime);
  dayjs.extend(Weekday);
  dayjs.locale('pl');

  app.use(VueApexCharts);
  Object.assign(get(window, 'Apex', {}), {
    chart: {
      locales: [await import('apexcharts/src/locales/pl.json')],
      defaultLocale: 'pl',
    },
  });

  await useInstallationStore().fetchInstallations();
});
