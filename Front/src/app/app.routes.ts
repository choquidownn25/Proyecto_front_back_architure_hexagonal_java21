import { Routes } from '@angular/router';
import { BlankComponent } from './layouts/blank/blank.component';
import { FullComponent } from './layouts/full/full.component';
import { AppSideLoginComponent } from './pages/authentication/side-login/side-login.component';
import { AppSideRegisterComponent } from './pages/authentication/side-register/side-register.component';


export const routes: Routes = [

  // 🔹 Redirección inicial
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full'
  },

  // 🔹 LOGIN (layout blanco)
  {
    path: 'login',
    component: AppSideLoginComponent,
    children: [
      {
        path: '',
        component: AppSideLoginComponent
      }
    ]
  },

  // 🔹 DASHBOARD (layout completo)
  {
    path: '',
    component: FullComponent,
    children: [
      {
        path: 'dashboard',
        loadChildren: () =>
          import('./pages/pages.routes')
            .then(m => m.PagesRoutes),
      },
      {
        path: 'ui-components',
        loadChildren: () =>
          import('./pages/ui-components/ui-components.routes')
            .then(m => m.UiComponentsRoutes),
      },
      {
        path: 'extra',
        loadChildren: () =>
          import('./pages/extra/extra.routes')
            .then(m => m.ExtraRoutes),
      }
    ]
  },
 {
    path: 'login',
    component: BlankComponent,
    children: [
      {
        path: '',
        component: AppSideLoginComponent
      }
    ]
  },

  // 🔹 Fallback
  {
    path: '**',
    redirectTo: 'login'
  }
];

