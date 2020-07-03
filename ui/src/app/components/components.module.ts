import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';
import {IonicModule} from '@ionic/angular';
import {HeadersComponent} from './headers/headers.component';
import {RouterModule} from '@angular/router';
import {NgxChartsModule} from '@swimlane/ngx-charts';
import {PieChartComponent} from './pie-chart/pie-chart.component';
import {VeritcalBarGraphComponent} from './veritcal-bar-graph/veritcal-bar-graph.component';

@NgModule({
    imports: [
        IonicModule,
        CommonModule,
        RouterModule,
        NgxChartsModule
    ],
    exports: [
        HeadersComponent,
        PieChartComponent,
        VeritcalBarGraphComponent
    ],
    declarations: [
        HeadersComponent,
        PieChartComponent,
        VeritcalBarGraphComponent
    ],
})
export class ComponentsModule {
}
