import {Routes} from '@angular/router';

import {RessourcePage} from './page/ressource-page/ressource-page';
import { ZonePage } from './page/zone/zone-page/zone-page';
import { AccueilPage } from './page/accueil-page/accueil-page';
import { AnimalPage } from './page/animal/animal-page/animal-page';

export const routes: Routes = [
  {path: '', redirectTo: 'accueil', pathMatch: 'full'},
  {path: 'accueil', component: AccueilPage, title: "Page d'acceuil"},
  {path: 'ressource', component: RessourcePage, title: 'Gestion des ressources'},
  {path: 'zone', component: ZonePage, title: 'Gestion des zones'},
  {path: 'animal', component: AnimalPage, title: 'Gestion des animaux'}
];
