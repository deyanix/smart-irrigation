<template>
  <q-page padding>
    <AppPageHeader>
      <template #breadcrumbs>
        <AppBreadcrumbs class="col">
          <AppBreadcrumbsEl :to="{ name: 'SectionList' }">
            Sekcje
          </AppBreadcrumbsEl>
          <AppBreadcrumbsEl> Nowa sekcja </AppBreadcrumbsEl>
        </AppBreadcrumbs>
      </template>
    </AppPageHeader>
    <div class="row q-col-gutter-md">
      <div class="col-12 col-md-10">
        <q-card flat bordered>
          <q-card-section>
            <div class="row q-col-gutter-md">
              <div class="col-12 col-md-2 text-weight-bold">Konfiguracja</div>
              <div class="col-12 col-md-10">
                <div class="row q-col-gutter-md">
                  <div class="col-12 col-md-8">
                    <q-input model-value="" label="Nazwa *" dense outlined />
                  </div>
                  <div class="col-12 col-md-4">
                    <q-select
                      model-value=""
                      label="GPIO"
                      dense
                      outlined
                      :options="[23, 24, 25, 8]"
                    />
                  </div>
                  <div class="col-12">
                    <q-input
                      model-value=""
                      type="textarea"
                      label="Opis"
                      dense
                      outlined
                    />
                  </div>
                </div>
              </div>
            </div>
          </q-card-section>
        </q-card>
      </div>
    </div>
    <AppPageActions>
      <q-btn label="Zapisz" color="primary" rounded unelevated />
    </AppPageActions>
  </q-page>
</template>
<script setup lang="ts"></script>
