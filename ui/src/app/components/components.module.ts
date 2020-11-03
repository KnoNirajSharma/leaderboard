import { CommonModule } from '@angular/common';
import { HeadersComponent } from './headers/headers.component';
import { IonicModule } from '@ionic/angular';
import { NgModule } from '@angular/core';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { PieChartComponent } from './pie-chart/pie-chart.component';
import { RouterModule } from '@angular/router';
import { VerticalBarGraphComponent } from './veritcal-bar-graph/vertical-bar-graph.component';
import { ListPaginatorComponent } from './list-paginator/list-paginator.component';
import { MedalTallyComponent } from './medal-tally/medal-tally.component';
import { BadgeDetailTableComponent } from './badge-detail-table/badge-detail-table.component';
import { CustomPipesModule } from '../pipe/custom-pipes.module';
import { MenuBoxComponent } from './menu-box/menu-box.component';
import { LegendTooltipComponent } from './legend-tooltip/legend-tooltip.component';
import {TableComponent} from './table/table.component';
import {NgxDatatableModule} from '@swimlane/ngx-datatable';

@NgModule({
  imports: [
    IonicModule,
    CommonModule,
    RouterModule,
    NgxChartsModule,
    CustomPipesModule,
    NgxDatatableModule
  ],
  declarations: [
    HeadersComponent,
    PieChartComponent,
    VerticalBarGraphComponent,
    ListPaginatorComponent,
    MedalTallyComponent,
    BadgeDetailTableComponent,
    MenuBoxComponent,
    LegendTooltipComponent,
    TableComponent
  ],
  exports: [
    HeadersComponent,
    PieChartComponent,
    VerticalBarGraphComponent,
    ListPaginatorComponent,
    MedalTallyComponent,
    BadgeDetailTableComponent,
    MenuBoxComponent,
    LegendTooltipComponent,
    TableComponent
  ],
})
export class ComponentsModule {
}
