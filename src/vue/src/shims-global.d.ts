import AppBreadcrumbs from 'components/AppBreadcrumbs/AppBreadcrumbs.vue';
import AppBreadcrumbsEl from 'components/AppBreadcrumbs/AppBreadcrumbsEl.vue';
import AppPageHeader from './components/AppPage/AppPageHeader.vue';
import AppPageHeaderButton from './components/AppPage/AppPageHeaderButton.vue';
import AppCardHeader from 'components/AppCard/AppCardHeader.vue';
import AppCard from 'components/AppCard/AppCard.vue';
import AppCardSection from 'components/AppCard/AppCardSection.vue';
import AppPageActions from 'components/AppPage/AppPageActions.vue';
import AppDialogWrapper from 'components/AppDialog/AppDialogWrapper.vue';
import AppDialogHeader from './components/AppDialog/AppDialogHeader.vue';
import AppDialogSection from 'components/AppDialog/AppDialogSection.vue';
import AppDialogActions from 'components/AppDialog/AppDialogActions.vue';

declare module 'vue' {
  export interface GlobalComponents {
    AppBreadcrumbs: typeof AppBreadcrumbs;
    AppBreadcrumbsEl: typeof AppBreadcrumbsEl;

    AppPageHeader: typeof AppPageHeader;
    AppPageHeaderButton: typeof AppPageHeaderButton;
    AppPageActions: typeof AppPageActions;

    AppCard: typeof AppCard;
    AppCardHeader: typeof AppCardHeader;
    AppCardSection: typeof AppCardSection;

    AppDialogWrapper: typeof AppDialogWrapper;
    AppDialogHeader: typeof AppDialogHeader;
    AppDialogSection: typeof AppDialogSection;
    AppDialogActions: typeof AppDialogActions;
  }
}
