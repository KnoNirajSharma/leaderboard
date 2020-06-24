import {Pipe, PipeTransform} from '@angular/core';
import {AuthorModel} from '../models/author.model';

@Pipe({
    name: 'firstWord'
})
export class FirstWordPipe implements PipeTransform {
    transform(InputString: string): any {
        return InputString.split(' ')[0];
}
}
