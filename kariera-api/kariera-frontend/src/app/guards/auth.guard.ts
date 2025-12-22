import {CanActivateFn, Router} from '@angular/router';
import {inject} from '@angular/core';
import {AuthService} from '../services/auth';

export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  //controlla se l'utente è loggato
  if (authService.isLoggedIn()) {
    return true;
  } else {
    //se non è loggato lo rimanda alla pagina di login
    router.navigate(['/login']);
    return false;
  }
};
