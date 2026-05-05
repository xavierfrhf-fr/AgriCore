import {Routes} from '@angular/router';

import {authGuard} from './guard/auth-guard';
import {AccueilPage} from './page/accueil-page/accueil-page';
import {RessourcePage} from './page/ressource-page/ressource-page';
import {ZonePage} from './page/zone/zone-page/zone-page';

export const routes: Routes = [

  {path: '', redirectTo: 'accueil', pathMatch: 'full'},

  {path: 'accueil', component: AccueilPage, title: 'Page d\'accueil'},

  {
    path: 'ressource',
    component: RessourcePage,
    title: 'Gestion des ressources',
    canActivate: [authGuard]
  },

  {
    path: 'zone',
    component: ZonePage,
    title: 'Gestion des zones',
    canActivate: [authGuard]
  }
];
