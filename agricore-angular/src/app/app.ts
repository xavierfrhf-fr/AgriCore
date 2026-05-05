import {Component, inject} from '@angular/core';
import {RouterOutlet} from '@angular/router';

import {NavigationAgricore} from './component/navigation/navigation';
import {Auth} from './service/auth';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, NavigationAgricore],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected authService: Auth = inject(Auth);
}
