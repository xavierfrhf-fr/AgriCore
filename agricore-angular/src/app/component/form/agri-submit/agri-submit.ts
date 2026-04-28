import {Component, EventEmitter, Input, Output} from '@angular/core';
import {FormGroup} from '@angular/forms';

@Component({
  selector: 'agri-submit',
  imports: [],
  templateUrl: './agri-submit.html',
  styleUrl: './agri-submit.css',
})
export class AgriSubmit {
  @Input() editing: boolean = false;
  @Input() form!: FormGroup;
  @Output() cancel: EventEmitter<void> = new EventEmitter<void>();

  public onCancelClick() {
    this.cancel.emit();
  }
}
