import {NgModule} from '@angular/core';

import {EmployeeFilterPipe} from './employee-filter/employee-filter.pipe';
import {ReverseListPipe} from './reverse-list/reverse-list.pipe';
import {SplitCamelCasePipe} from './split-camel-case/split-camel-case.pipe';

@NgModule({
    exports: [ReverseListPipe, EmployeeFilterPipe, SplitCamelCasePipe],
    declarations: [ReverseListPipe, EmployeeFilterPipe, SplitCamelCasePipe],
})
export class CustomPipesModule {
}
