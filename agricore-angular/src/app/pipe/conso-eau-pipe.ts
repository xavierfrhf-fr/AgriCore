import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'consoEau',
})
export class ConsoEauPipe implements PipeTransform {
  transform(value: number, ...args: unknown[]): number[] {
    let output:Array<number> = new Array<number>();
    output.push(1);
    if (value >= 6){
      output.push(2);
    }
    if (value >= 10) {
      output.push(3);
    }
    if (value >= 15) {
      output.push(4);
    }
    if (value >= 25) {
      output.push(5);
    }
    return output;
  }
}
