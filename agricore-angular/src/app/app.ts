import { Component, HostListener, signal } from '@angular/core';
import { NavigationEnd, Router, RouterOutlet } from '@angular/router';
import { filter } from 'rxjs';

import { Footer } from './component/footer/footer';
import { Header } from './component/header/header';
import { NavigationAgricore } from './component/navigation/navigation';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NavigationAgricore, Header, Footer],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App {
  protected readonly title = signal('AgriCore');
  protected fondTransform = 'translate(0px, 0px)';

  @HostListener('window:scroll')
  onScroll(): void {
    const x = window.scrollY * 0.04;
    this.fondTransform = `translate(${x}px, 0px)`;
  }

  affichNav = true;
  affichHeader = true;
  affichFooter = true;
  affichFond = true;

  private routesWithoutNavbar: string[] = ['/accueil', '/boutique', '/boutique/panier'];
  private routesWithoutHeader: string[] = ['/accueil', '/boutique', '/boutique/panier', '/boutique/vente'];
  private routesWithoutFooter: string[] = ['/accueil'];
  private routesWithoutFond: string[] = ['/accueil', '/boutique', '/boutique/panier', '/boutique/vente'];

  constructor(private router: Router) {
    this.router.events
      .pipe(filter((event) => event instanceof NavigationEnd))
      .subscribe((event: NavigationEnd) => {
        this.affichNav = !this.routesWithoutNavbar.includes(event.urlAfterRedirects);
        this.affichHeader = !this.routesWithoutHeader.includes(event.urlAfterRedirects);
        this.affichFooter = !this.routesWithoutFooter.includes(event.urlAfterRedirects);
        this.affichFond = !this.routesWithoutFond.includes(event.urlAfterRedirects);
      });
  }
}
