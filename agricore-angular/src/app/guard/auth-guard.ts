import {inject} from '@angular/core';
import {CanActivateFn, Router} from '@angular/router';

import {Auth} from '../service/auth';

export const authGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const authService = inject(Auth);

  if (authService.isLogged()) {
    return true;
  }

  return router.createUrlTree(['/accueil']);
};
