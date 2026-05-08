import { Component, Input } from '@angular/core';
import { TransformationDataDto } from '../../dto/ressource/transformation-data-dto';

@Component({
  selector: 'app-transformation-display',
  imports: [],
  templateUrl: './transformation-display.html',
  styleUrl: './transformation-display.css',
})
export class TransformationDisplay {
  @Input() transformationDataDTO!:TransformationDataDto;
}
