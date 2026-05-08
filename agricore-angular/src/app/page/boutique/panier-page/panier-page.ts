import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Header, SloganItem } from '../../../component/header/header';
import { Auth } from '../../../service/auth';

@Component({
  selector: 'app-panier-page',
  imports: [Header, RouterLink],
  templateUrl: './panier-page.html',
  styleUrl: './panier-page.css',
})
export class PanierPage implements OnInit, OnDestroy {
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

  logout(): void {
    this.auth.logout();
  }
}
