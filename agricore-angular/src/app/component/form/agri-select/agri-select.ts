import {CommonModule} from '@angular/common';
import {Component, ContentChild, Input, TemplateRef} from '@angular/core';
import {ReactiveFormsModule} from '@angular/forms';

import {AgriFormField} from '../agri-form-field/agri-form-field';

@Component({
  selector: 'agri-select',
  imports: [AgriFormField, CommonModule, ReactiveFormsModule],
  templateUrl: './agri-select.html',
  styleUrl: './agri-select.css',
})
export class AgriSelect extends AgriFormField {
  @Input() items: any[] = [];
  @Input('item-value') itemValue: string = '';
  @Input() nullable = false;
  @Input('null-label') nullLabel = '-- Aucune sélection --';

  @ContentChild(TemplateRef) itemTemplate?: TemplateRef<unknown>;

  public resolveValue(item: any): any {
    if (!item || !this.itemValue) {
      return item;
    }

    return item[this.itemValue];
  }
}
