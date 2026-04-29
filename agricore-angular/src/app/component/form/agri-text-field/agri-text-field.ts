import {Component, Input} from '@angular/core';
import {FormControl, ReactiveFormsModule} from '@angular/forms';

import {AgriFormField} from '../agri-form-field/agri-form-field';

@Component({
  selector: 'agri-text-field',
  imports: [AgriFormField, ReactiveFormsModule],
  templateUrl: './agri-text-field.html',
  styleUrl: './agri-text-field.css',
})
export class AgriTextField extends AgriFormField {
  @Input() type: string = 'text';
}
