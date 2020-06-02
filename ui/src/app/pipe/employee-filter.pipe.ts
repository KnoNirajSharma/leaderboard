import {Pipe, PipeTransform} from '@angular/core';
import {AuthorModel} from '../models/author.model';

@Pipe({
    name: 'employeeFilter'
})
export class EmployeeFilterPipe implements PipeTransform {
    transform(employees: AuthorModel[], searchTerm: string): any {
        if (!employees || !searchTerm) {
            return employees;
        }
        return employees.filter(employee =>
            employee.knolderName.toLowerCase().indexOf(searchTerm.toLowerCase()) !== -1);
    }
}
