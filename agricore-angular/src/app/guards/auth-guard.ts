import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { Auth } from '../service/auth';

export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(Auth);
  const router = inject(Router);

  // Vérifier si l'utilisateur est connecté
  if (!authService.isLoggedIn()) {
    router.navigate(['/accueil']);
    return false;
  }

  // Vérifier les rôles requis pour la route
  const requiredRoles = route.data?.['roles'] as string[];
  if (requiredRoles && requiredRoles.length > 0) {
    const userRole = authService.getRole();
    if (!userRole || !requiredRoles.includes(userRole)) {
      // Rediriger vers une page appropriée selon le rôle
      if (authService.isFermier()) {
        router.navigate(['/gestion-employes']);
      } else {
        router.navigate(['/animal']);
      }
      return false;
    }
  }

  return true;
};

// Guards spécifiques pour les rôles
export const fermierGuard: CanActivateFn = (route, state) => {
  const authService = inject(Auth);
  const router = inject(Router);

  if (!authService.isLoggedIn()) {
    router.navigate(['/accueil']);
    return false;
  }

  if (!authService.isFermier()) {
    router.navigate(['/animal']);
    return false;
  }

  return true;
};

export const employeGuard: CanActivateFn = (route, state) => {
  const authService = inject(Auth);
  const router = inject(Router);

  if (!authService.isLoggedIn()) {
    router.navigate(['/accueil']);
    return false;
  }

  if (!authService.isEmploye()) {
    router.navigate(['/animal']);
    return false;
  }

  return true;
};

export const clientGuard: CanActivateFn = (route, state) => {
  const authService = inject(Auth);
  const router = inject(Router);

  if (!authService.isLoggedIn()) {
    router.navigate(['/accueil']);
    return false;
  }

  if (!authService.isClient()) {
    router.navigate(['/animal']);
    return false;
  }

  return true;
};
