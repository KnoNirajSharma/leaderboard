import { CommonModule } from '@angular/common';
import { HeadersComponent } from './headers/headers.component';
import { IonicModule } from '@ionic/angular';
import { NgModule } from '@angular/core';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { PieChartComponent } from './pie-chart/pie-chart.component';
import { RouterModule } from '@angular/router';
import { VerticalBarGraphComponent } from './veritcal-bar-graph/vertical-bar-graph.component';
import { MedalTallyComponent } from './medal-tally/medal-tally.component';
import { BadgeDetailTableComponent } from './badge-detail-table/badge-detail-table.component';

@NgModule({
  imports: [
    IonicModule,
    CommonModule,
    RouterModule,
    NgxChartsModule,
  ],
  declarations: [
    HeadersComponent,
    PieChartComponent,
    VerticalBarGraphComponent,
    MedalTallyComponent,
    BadgeDetailTableComponent
  ],
  exports: [
    HeadersComponent,
    PieChartComponent,
    VerticalBarGraphComponent,
    MedalTallyComponent,
    BadgeDetailTableComponent
  ],
})
export class ComponentsModule {
}
