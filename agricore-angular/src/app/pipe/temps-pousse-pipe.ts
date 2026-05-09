import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'tempsPousse',
})
export class TempsPoussePipe implements PipeTransform {
  transform(minTot: number, ): unknown {
    var heure:number = 0;
    while (minTot >= 60){
      heure += 1;
      minTot -= 60;
    }
    var output:string = "";
    if (heure >= 1){
      output += heure + "h ";
    }
    if (minTot >= 1){
      output += minTot+"mins "
    }
    return output;
  }
}
