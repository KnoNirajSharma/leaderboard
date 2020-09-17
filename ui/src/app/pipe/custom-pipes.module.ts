import { NgModule } from '@angular/core';
import { ReverseListPipe } from './reverse-list.pipe';
import { EmployeeFilterPipe } from './employee-filter.pipe';

@NgModule({
  exports: [ReverseListPipe, EmployeeFilterPipe],
  declarations: [ReverseListPipe, EmployeeFilterPipe],
})
export class CustomPipesModule {
}
