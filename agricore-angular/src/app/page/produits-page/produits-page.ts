import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Header, SloganItem } from '../../component/header/header';
import { Auth } from '../../service/auth';

@Component({
  selector: 'app-produits-page',
  imports: [Header, RouterLink],
  templateUrl: './produits-page.html',
  styleUrl: './produits-page.css',
})
export class ProduitsPage implements OnInit, OnDestroy {
  private auth = inject(Auth);

  protected sloganItems: SloganItem[] = [
    { iconSrc: 'assets/image/maison.png', texte: 'Production locale' },
    { iconSrc: 'assets/image/boutique/vache.png', texte: 'Bien-être animal' },
    { iconSrc: 'assets/image/boutique/feuille.png', texte: 'Zéro produit chimique' },
  ];

  ngOnInit(): void {
    document.body.style.backgroundColor = '#ebdbc8';
  }

  ngOnDestroy(): void {
    document.body.removeAttribute('style');
  }

  quantites = {
    fraise: 0,
    laitVache: 0,
    laitBrebis:0,
    fromage: 0,
    poire: 0,
    pomme: 0,
    mais: 0,
    oeufs: 0,
    jusPomme: 0,
    huileTournesol:0,
    farineBle: 0,
    pates:0,
    huileColza:0
  };

  augmente(produit: keyof typeof this.quantites) {
    this.quantites[produit]++;
  }

  diminue(produit: keyof typeof this.quantites) {
    if (this.quantites[produit] > 0) {
      this.quantites[produit]--;
    }
  }

  logout(): void {
    this.auth.logout();
  }
}
