import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Header, SloganItem } from '../../../component/header/header';
import { Auth } from '../../../service/auth';

@Component({
  selector: 'app-vente-page',
  imports: [Header, RouterLink],
  templateUrl: './vente-page.html',
  styleUrl: './vente-page.css',
})
export class VentePage implements OnInit, OnDestroy {
  private auth = inject(Auth);

  protected sloganItems: SloganItem[] = [
    { iconSrc: 'assets/image/inventaire.png', texte: 'Gestion des produits du magasin' },
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
