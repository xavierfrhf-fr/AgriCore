import { Component, OnDestroy, OnInit } from '@angular/core';

@Component({
  selector: 'app-panier-page',
  imports: [],
  templateUrl: './panier-page.html',
  styleUrl: './panier-page.css',
})
export class PanierPage implements OnInit, OnDestroy {
  ngOnDestroy(): void {
    document.body.removeAttribute('style');
  }
  ngOnInit(): void {
    document.body.style.backgroundColor = '#ebdbc8';
  }
}
