import { Pipe, PipeTransform } from '@angular/core';
import { ZoneDataDTO } from '../dto/zone/response/zone-data-dto';

@Pipe({
  name: 'zoneInfo',
})
export class ZoneInfoPipe implements PipeTransform {
  transform(value: ZoneDataDTO): string {
    let output: string = '';
    if (value.zoneUnique) {
      output += '(Unique - ';
    }else{
      output += '('
    }
    if (value.typeZone === 'STORAGE') {
      output += 'Batiment de stockage';
    } else if (value.typeZone === 'UTILITY'){
      output += 'Batiment utilitaire';
    } else if (value.typeZone === 'ANIMAL'){
      output += "Batiement d'elevage";
    } else if (value.typeZone === 'PLANTS'){
      output += 'Batiment de culture';
    }else {
      output += 'Batiment spécial'
    }

    output += ')'

    return output;
  }
}
