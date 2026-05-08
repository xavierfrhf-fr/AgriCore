import { Component, Input } from '@angular/core';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';

export interface SloganItem {
  iconSrc: string;
  texte: string;
  lien?: string;
}

@Component({
  selector: 'app-header',
  imports: [RouterLink, CommonModule],
  templateUrl: './header.html',
  styleUrl: './header.css',
})
export class Header {
  @Input() logoTexte: string = "La ferme c'est vous !";

  @Input() sloganItems: SloganItem[] = [
    { iconSrc: 'assets/image/maison.png', texte: 'Gestion', lien: '/gestion-employes' },
    { iconSrc: 'assets/image/inventaire.png', texte: 'Organisation' },
    { iconSrc: 'assets/image/liste.png', texte: 'Suivi' },
  ];
}
