import { Component, EventEmitter, Input, Output } from '@angular/core';
import {RouterLink, RouterLinkActive} from '@angular/router';

@Component({
  selector: 'navigation',
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './navigation.html',
  styleUrl: './navigation.css',
})
export class NavigationAgricore {
  @Input() menuOuvert:boolean = true;
  @Output() toggleMenuOuvert: EventEmitter<void> = new EventEmitter<void>;

  protected toggleMenu(){
    this.toggleMenuOuvert.emit();
  }
}
