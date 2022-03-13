import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'mapKeyValue', pure: true
})
export class MapKeyValuePipe implements PipeTransform {

  transform(value: Map<any,any>) {
    return Array.from(value.entries());
  }

}
