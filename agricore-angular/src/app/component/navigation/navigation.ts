import { Component, HostListener } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'navigation',
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './navigation.html',
  styleUrl: './navigation.css',
})
export class NavigationAgricore {
  menuOuvert: boolean = true;

  protected toggleMenu(): void {
    this.menuOuvert = !this.menuOuvert;
  }

  @HostListener('window:scroll')
  onScroll(): void {
    this.menuOuvert = window.scrollY < 50;
  }
}
