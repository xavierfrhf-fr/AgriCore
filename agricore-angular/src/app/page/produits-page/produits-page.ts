import { Component } from '@angular/core';



@Component({
  selector: 'app-produits-page',
  imports: [],
  templateUrl: './produits-page.html',
  styleUrl: './produits-page.css',
})
export class ProduitsPage {


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
}
