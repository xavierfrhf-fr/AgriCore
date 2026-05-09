import { Routes } from '@angular/router';

import { RessourcePage } from './page/ressource-page/ressource-page';
import { ZonePage } from './page/zone/zone-page/zone-page';
import { AccueilPage } from './page/accueil-page/accueil-page';
import { VehiculePage } from './page/vehicule-page/vehicule-page';
import { PanierPage } from './page/boutique/panier-page/panier-page';
import { AnimalPage } from './page/animal/animal-page/animal-page';
import { ProduitsPage } from './page/produits-page/produits-page';
import { PlantePage } from './page/plante-page/plante-page';
import { GestionEmployes } from './page/gestion-employes/gestion-employes';
import { fermierGuard } from './guards/auth-guard';
import { employeGuard } from './guards/auth-guard';
import { clientGuard } from './guards/auth-guard';
import { VentePage } from './page/boutique/vente-page/vente-page';

export const routes: Routes = [
  { path: '', redirectTo: 'accueil', pathMatch: 'full' },
  { path: 'accueil', component: AccueilPage, title: "Page d'accueil" },
  {
    path: 'gestion-employes',
    component: GestionEmployes,
    title: 'Gestion des employés',
    canActivate: [fermierGuard],
  },
  { path: 'ressource', component: RessourcePage, title: 'Gestion des ressources' },
  { path: 'zone', component: ZonePage, title: 'Gestion des zones' },
  { path: 'vehicule', component: VehiculePage, title: 'Gestion des véhicules' },
  { path: 'boutique/panier', component: PanierPage, title: 'Page validation du panier' },
  { path: 'boutique/vente', component: VentePage, title: 'Gestion des ventes' },
  { path: 'animal', component: AnimalPage, title: 'Gestion des animaux' },
  { path: 'boutique', component: ProduitsPage, title: 'Boutique' },
  { path: 'plante', component: PlantePage, title: 'Gestion des plantes' },
];
