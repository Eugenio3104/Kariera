import {CanActivateFn, Router} from '@angular/router';
import {inject} from '@angular/core';
import {AuthService} from '../services/auth';
import {map, catchError, of} from 'rxjs';

export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  // Se l'utente è già loggato in memoria, lascialo passare
  if (authService.isLoggedIn()) {
    return true;
  }

  // Altrimenti verifica con il backend
  return authService.checkSession().pipe(
    map((user: any) => {
      // Sessione valida, salva l'utente
      authService.setLoggedUser(user);
      return true;
    }),
    catchError(() => {
      // Nessuna sessione valida, redirect al login
      router.navigate(['/login']);
      return of(false);
    })
  );
};
