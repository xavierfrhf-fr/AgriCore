import {Routes} from '@angular/router';

import {RessourcePage} from './page/ressource-page/ressource-page';
import { ZonePage } from './page/zone/zone-page/zone-page';

export const routes: Routes = [
  {path: 'ressource', component: RessourcePage, title: 'Gestion des ressources'},
  {path: 'zone', component: ZonePage, title: 'Gestion des zones'}
];
