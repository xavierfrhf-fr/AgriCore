import { Routes } from '@angular/router';

<<<<<<< HEAD
import { RessourcePage } from './page/ressource-page/ressource-page';
import { ZonePage } from './page/zone/zone-page/zone-page';
import { AccueilPage } from './page/accueil-page/accueil-page';
import { VehiculePage } from './page/vehicule-page/vehicule-page';
import { PanierPage } from './page/boutique/panier-page/panier-page';

export const routes: Routes = [
  { path: '', redirectTo: 'accueil', pathMatch: 'full' },
  { path: 'accueil', component: AccueilPage, title: "Page d'acceuil" },
  { path: 'ressource', component: RessourcePage, title: 'Gestion des ressources' },
  { path: 'zone', component: ZonePage, title: 'Gestion des zones' },
  { path: 'vehicule', component: VehiculePage, title: 'Gestion des véhicules' },
  { path: 'boutique/panier', component: PanierPage, title: 'Page validation du panier' },
=======
import {AccueilPage} from './page/accueil-page/accueil-page';
import {AnimalPage} from './page/animal/animal-page/animal-page';
import {RessourcePage} from './page/ressource-page/ressource-page';
import {VehiculePage} from './page/vehicule-page/vehicule-page';
import {ZonePage} from './page/zone/zone-page/zone-page';

export const routes: Routes = [
  {path: '', redirectTo: 'accueil', pathMatch: 'full'},
  {path: 'accueil', component: AccueilPage, title: 'Page d\'accueil'}, {
    path: 'ressource',
    component: RessourcePage,
    title: 'Gestion des ressources'
  },
  {path: 'zone', component: ZonePage, title: 'Gestion des zones'},
  {path: 'animal', component: AnimalPage, title: 'Gestion des animaux'},
  {path: 'vehicule', component: VehiculePage, title: 'Gestion des véhicules'}
>>>>>>> main
];
