import { NgModule } from '@angular/core';

import { EmployeeFilterPipe } from './employee-filter.pipe';
import { ReverseListPipe } from './reverse-list.pipe';
import { SplitCamelCasePipe } from './splitCamelCase.pipe';

@NgModule({
  exports: [ReverseListPipe, EmployeeFilterPipe, SplitCamelCasePipe],
  declarations: [ReverseListPipe, EmployeeFilterPipe, SplitCamelCasePipe],
})
export class CustomPipesModule {
}
