import { Component, EventEmitter, Input, Output } from '@angular/core';
import {RouterLink, RouterLinkActive} from '@angular/router';

@Component({
  selector: 'navigation',
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './navigation.html',
  styleUrl: './navigation.css',
})
export class NavigationAgricore {
  menuOuvert:boolean = true;


  protected toggleMenu(){
    this.menuOuvert = !this.menuOuvert;
  }
}
