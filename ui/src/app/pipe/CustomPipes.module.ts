import { NgModule } from '@angular/core';
import { ReverseListPipe } from './reverse-list.pipe';

@NgModule({
  exports: [ReverseListPipe],
  declarations: [ReverseListPipe],
})
export class CustomPipesModule {
}
