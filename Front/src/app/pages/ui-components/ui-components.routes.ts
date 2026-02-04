import { Routes } from '@angular/router';

// ui
import { AppBadgeComponent } from './badge/badge.component';
import { AppChipsComponent } from './chips/chips.component';
import { AppListsComponent } from './lists/lists.component';
import { AppMenuComponent } from './menu/menu.component';
import { AppTooltipsComponent } from './tooltips/tooltips.component';
import { AppFormsComponent } from './forms/forms.component';
import { AppTablesComponent } from './tables/tables.component';
import { CrudComponent } from './crud/crud.component';
import { AppKichenSinkComponent } from './crud/datatable/kichen-sink/kichen-sink.component';
import { BackedComponent } from './crud/datatable/backed/backed.component';
import { GraficoComponent } from './crud/grafico/grafico.component';
import { GraficBarComponent } from './crud/grafic-bar/grafic-bar.component';
import { VideoComponent } from './crud/video/video.component';


import { AppBlogCardsComponent } from 'src/app/components/blog-card/blog-card.component';
import { AppFullcalendarComponent } from './calendar/fullcalendar/fullcalendar.component';
import { provideNativeDateAdapter } from '@angular/material/core';
import { AppCalendarComponent } from './calendar/calendar-full';
import { DialogCalendarComponent } from './calendar/dialog-calendar-full';
import { TimeDialogCalendarComponent } from './calendar/fullcalendar/time-calendar/time-dialog-calendar-full';

export const UiComponentsRoutes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'badge',
        component: AppBadgeComponent,
      },
      {
        path: 'chips',
        component: AppChipsComponent,
      },
      {
        path: 'crud',
        component: AppKichenSinkComponent,
      },
      {
        path: 'backed',
        component: BackedComponent,
      },
      {
        path: 'grafico',
        component: GraficoComponent,
      },
      {
        path: 'grafico-bar',
        component: GraficBarComponent,
      },
      {

        path: 'video',
        component: VideoComponent,
      },
      {
        path: 'blobt',
        component: AppBlogCardsComponent,
      },
      {
        path: 'calendar',
        component: AppCalendarComponent,

      },
      {
        path: 'calendar-time',
        component: TimeDialogCalendarComponent,
      },
      {
        path: 'calendar-dialog',
        component: DialogCalendarComponent,
      },
      {
        path: 'lists',
        component: AppListsComponent,
      },
      {
        path: 'menu',
        component: AppMenuComponent,
      },
      {
        path: 'tooltips',
        component: AppTooltipsComponent,
      },
      {
        path: 'forms',
        component: AppFormsComponent,
      },
      {
        path: 'tables',
        component: AppTablesComponent,
      },
    ],
  },
];
