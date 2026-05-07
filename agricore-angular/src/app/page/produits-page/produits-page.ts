import { Component } from '@angular/core';



@Component({
  selector: 'app-produits-page',
  imports: [],
  templateUrl: './produits-page.html',
  styleUrl: './produits-page.css',
})
export class ProduitsPage {
quantites = {
    fraise: 0,
    lait: 0,
    fromage: 0,
    poire: 0,
    pomme: 0,
    mais: 0
  };

  augmente(produit: keyof typeof this.quantites) {
    this.quantites[produit]++;
  }

  diminue(produit: keyof typeof this.quantites) {
    if (this.quantites[produit] > 0) {
      this.quantites[produit]--;
    }
  }
}
/*
    qteFraise = 0;
  qteLait = 0;
  qteFromage = 0;

  augmente(qte: number): number {
    return qte + 1;
  }

  diminue(qte: number): number {
    if (qte > 0) {
      return qte - 1;
    }
    return 0;
  }
}*/
