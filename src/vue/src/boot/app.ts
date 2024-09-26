import { boot } from 'quasar/wrappers';
import { useInstallationStore } from 'stores/installation';
import dayjs from 'dayjs';
import Duration from 'dayjs/plugin/duration';
import CustomParseFormat from 'dayjs/plugin/customParseFormat';
import RelativeTime from 'dayjs/plugin/relativeTime';
import 'dayjs/locale/pl';

export default boot(async () => {
  dayjs.extend(Duration);
  dayjs.extend(CustomParseFormat);
  dayjs.extend(RelativeTime);
  dayjs.locale('pl');

  await useInstallationStore().fetchInstallations();
});
