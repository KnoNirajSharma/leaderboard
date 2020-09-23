import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'splitCamelCase'
})
export class SplitCamelCasePipe implements PipeTransform {
  transform(stringInCamelCase: string): string {
    return stringInCamelCase.split(/(?=[A-Z])/).join(' ');
  }
}
