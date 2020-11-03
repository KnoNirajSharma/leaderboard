import { NgModule } from '@angular/core';
import { ReverseListPipe } from './reverse-list.pipe';
import { EmployeeFilterPipe } from './employee-filter.pipe';
import { SplitCamelCasePipe } from './splitCamelCase.pipe';

@NgModule({
  exports: [ReverseListPipe, EmployeeFilterPipe, SplitCamelCasePipe],
  declarations: [ReverseListPipe, EmployeeFilterPipe, SplitCamelCasePipe],
})
export class CustomPipesModule {
}
