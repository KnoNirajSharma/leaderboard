import {CommonModule} from '@angular/common';
import {HeadersComponent} from './headers/headers.component';
import {IonicModule} from '@ionic/angular';
import {NgModule} from '@angular/core';
import {NgxChartsModule} from '@swimlane/ngx-charts';
import {PieChartComponent} from './pie-chart/pie-chart.component';
import {RouterModule} from '@angular/router';
import {VerticalBarGraphComponent} from './veritcal-bar-graph/vertical-bar-graph.component';

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
        VerticalBarGraphComponent
    ],
    declarations: [
        HeadersComponent,
        PieChartComponent,
        VerticalBarGraphComponent
    ],
})
export class ComponentsModule {
}
