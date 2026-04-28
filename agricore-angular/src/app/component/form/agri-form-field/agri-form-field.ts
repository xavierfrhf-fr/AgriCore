import {KeyValuePipe} from '@angular/common';
import {Component, Input} from '@angular/core';
import {FormControl} from '@angular/forms';
import {minLength, required} from '@angular/forms/signals';

@Component({
  selector: 'agri-form-field',
  imports: [KeyValuePipe],
  templateUrl: './agri-form-field.html',
  styleUrl: './agri-form-field.css',
})
export class AgriFormField {
  @Input() label!: string;
  @Input() id!: string;
  @Input() control!: FormControl;

  @Input()
  errorMessages: Record<string, string> = {
    required: 'Ce champ est obligatoire',
    minlength: 'Ce champ a une taille minimum',
    min: 'Ce champ a une valeur minimum',
    max: 'Ce champ a une valeur maximum'
  };
}
