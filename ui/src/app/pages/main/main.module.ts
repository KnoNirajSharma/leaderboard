import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import { RouterModule, Routes } from '@angular/router';
import { MainPage } from './main.page';
import { TableComponent } from '../../components/table/table.component';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { ComponentsModule } from '../../components/components.module';
import { CustomPipesModule } from '../../pipe/custom-pipes.module';
import {LegendTooltipComponent} from '../../components/legend-tooltip/legend-tooltip.component';
import { HoverDirective } from '../../hover.directive';
import {OverlayModule} from '@angular/cdk/overlay';
import {AwesomeTooltipDirective} from '../../tooltip-directive';

const routes: Routes = [
  {
    path: '',
    component: MainPage,
  },
];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    IonicModule,
    RouterModule.forChild(routes),
    NgxDatatableModule,
    ComponentsModule,
    CustomPipesModule,
    OverlayModule
  ],
  declarations: [MainPage, TableComponent, HoverDirective, AwesomeTooltipDirective],
  entryComponents: [LegendTooltipComponent]
})
export class MainPageModule {
}
