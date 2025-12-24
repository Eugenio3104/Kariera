import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login';
import { RegisterComponent } from './components/register/register';
import { CoursesComponent } from './components/courses/courses';
import {authGuard} from './guards/auth.guard';

export const routes: Routes = [
  {path: 'login' , component : LoginComponent},
  {path: 'registration' , component : RegisterComponent},
  {path: 'courses' , component : CoursesComponent , canActivate: [authGuard]},
  {path: '' , redirectTo: '/login', pathMatch: 'full'}
];
