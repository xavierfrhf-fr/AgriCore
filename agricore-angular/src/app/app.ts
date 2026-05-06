import {Component, signal} from '@angular/core';
import { NavigationEnd, Router, RouterOutlet} from '@angular/router';
import { NavigationAgricore } from './component/navigation/navigation';
import { filter } from 'rxjs';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NavigationAgricore],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App {
  protected readonly title = signal('AgriCore');
  affichNav = true;
  menuOuvert:boolean = true;

  protected toggleMenu(){
    this.menuOuvert = !this.menuOuvert;
  }

  private routesWithoutNavbar:string[] = ['/accueil','/boutique/panier'];

  constructor(private router: Router) {
    this.router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe((event: NavigationEnd) => {
        this.affichNav = !this.routesWithoutNavbar.includes(event.urlAfterRedirects);
      });
  }
}
