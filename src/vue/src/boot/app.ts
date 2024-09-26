import { boot } from 'quasar/wrappers';
import { useInstallationStore } from 'stores/installation';
import dayjs from 'dayjs';
import duration from 'dayjs/plugin/duration';
import customParseFormat from 'dayjs/plugin/customParseFormat';

export default boot(async () => {
  dayjs.extend(duration);
  dayjs.extend(customParseFormat);

  await useInstallationStore().fetchInstallations();
});
