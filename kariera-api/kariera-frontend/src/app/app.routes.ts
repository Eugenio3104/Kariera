import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login';
import { RegisterComponent } from './components/register/register';
import { CoursesComponent } from './components/courses/courses';
import { authGuard } from './guards/auth.guard';
import { MainLayoutComponent } from './layouts/main-layout/main-layout';

export const routes: Routes = [
  // Public routes
  { path: 'login', component: LoginComponent },
  { path: 'registration', component: RegisterComponent },
  { path: 'courses', component: CoursesComponent, canActivate: [authGuard] },


  {
    path: 'app',
    component: MainLayoutComponent,
    canActivate: [authGuard],
    children: [
      {
        path: 'dashboard',
        loadComponent: () => import('./components/dashboard/dashboard').then(m => m.DashboardComponent)
      },
      {
        path: 'libretto',
        loadComponent: () => import('./components/libretto/libretto').then(m => m.LibrettoComponent)
      },
      {
        path: 'prediction',
        loadComponent: () => import('./components/prediction/prediction').then(m => m.PredictionComponent)
      },
      {
        path: 'class-schedule',
        loadComponent: () => import('./components/class-schedule/class-schedule').then(m => m.ClassScheduleComponent)
      },

      { path: '', redirectTo: 'dashboard', pathMatch: 'full' }
    ]
  },


  { path: '', redirectTo: '/login', pathMatch: 'full' }
];
