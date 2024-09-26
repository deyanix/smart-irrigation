import { boot } from 'quasar/wrappers';
import AppBreadcrumbs from 'components/AppBreadcrumbs/AppBreadcrumbs.vue';
import AppBreadcrumbsEl from 'components/AppBreadcrumbs/AppBreadcrumbsEl.vue';
import AppPageHeader from 'src/components/AppPage/AppPageHeader.vue';
import AppPageHeaderButton from 'src/components/AppPage/AppPageHeaderButton.vue';
import AppCard from 'components/AppCard/AppCard.vue';
import AppCardHeader from 'components/AppCard/AppCardHeader.vue';
import AppCardSection from 'components/AppCard/AppCardSection.vue';
import AppPageActions from 'components/AppPage/AppPageActions.vue';
import AppDialogHeader from 'src/components/AppDialog/AppDialogHeader.vue';
import AppDialogWrapper from 'components/AppDialog/AppDialogWrapper.vue';
import AppDialogSection from 'components/AppDialog/AppDialogSection.vue';
import AppDialogActions from 'components/AppDialog/AppDialogActions.vue';
import AppDateTimeInput from 'components/AppInput/AppDateTimeInput.vue';

export default boot(({ app }) => {
  app.component('AppBreadcrumbs', AppBreadcrumbs);
  app.component('AppBreadcrumbsEl', AppBreadcrumbsEl);

  app.component('AppPageHeader', AppPageHeader);
  app.component('AppPageHeaderButton', AppPageHeaderButton);
  app.component('AppPageActions', AppPageActions);

  app.component('AppCard', AppCard);
  app.component('AppCardHeader', AppCardHeader);
  app.component('AppCardSection', AppCardSection);

  app.component('AppDialogWrapper', AppDialogWrapper);
  app.component('AppDialogHeader', AppDialogHeader);
  app.component('AppDialogSection', AppDialogSection);
  app.component('AppDialogActions', AppDialogActions);

  app.component('AppDateTimeInput', AppDateTimeInput);
});
