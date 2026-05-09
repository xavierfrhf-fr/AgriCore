import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'consoEau',
})
export class ConsoEauPipe implements PipeTransform {
  transform(value: number, ...args: unknown[]): number[] {
    let output:Array<number> = new Array<number>();
    output.push(1);
    if (value >= 0.02){
      output.push(2);
    }
    if (value >= 0.05){
      output.push(3);
    }
    return output;
  }
}
