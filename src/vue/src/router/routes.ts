import { RouteRecordRaw } from 'vue-router';

const routes: RouteRecordRaw[] = [
  {
    path: '/',
    component: () => import('layouts/MainLayout.vue'),
    children: [
      {
        name: 'Dashboard',
        path: '/',
        component: () => import('pages/Dashboard/DashboardPage.vue'),
      },
      {
        name: 'SectionList',
        path: '/sections',
        component: () => import('pages/Section/List/SectionList.vue'),
      },
      {
        name: 'SectionCreate',
        path: '/sections/create',
        component: () => import('pages/Section/Create/SectionCreate.vue'),
      },
      {
        name: 'SectionPreview',
        path: '/sections/:id',
        component: () => import('pages/Section/Preview/SectionPreview.vue'),
      },
      {
        name: 'SectionSchedules',
        path: '/sections/:id/schedules',
        component: () =>
          import('pages/Section/Preview/Schedules/SectionSchedules.vue'),
      },
      {
        name: 'SectionIrrigations',
        path: '/sections/:id/irrigations',
        component: () =>
          import('pages/Section/Preview/Irrigations/SectionIrrigations.vue'),
      },
      {
        name: 'SectionUpcomingIrrigations',
        path: '/sections/:id/upcoming-irrigations',
        component: () =>
          import(
            'pages/Section/Preview/UpcomingIrrigations/SectionUpcomingIrrigations.vue'
          ),
      },
    ],
  },

  {
    path: '/:catchAll(.*)*',
    component: () => import('pages/ErrorNotFound.vue'),
  },
];

export default routes;
