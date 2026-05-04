import {CommonModule} from '@angular/common';
import {Component, EventEmitter, HostListener, Input, Output} from '@angular/core';
import {FormGroup, ReactiveFormsModule} from '@angular/forms';

@Component({
  selector: 'agri-modal',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './agri-modal.html',
  styleUrl: './agri-modal.css',
})
export class AgriModal {
  @Input() title: string = '';
  @Input() form!: FormGroup;

  @Output() closed: EventEmitter<void> = new EventEmitter<void>();

  @HostListener('document:keydown.escape', ['$event'])
  protected onEscape(event: Event) {
    event.preventDefault();
    this.onClose();
  }

  public onClose() {
    this.closed.emit();
  }
}