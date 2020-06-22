import {CommonModule, DatePipe} from '@angular/common';
import {NgModule} from '@angular/core';
import {IonicModule} from '@ionic/angular';
import {HeadersComponent} from './headers/headers.component';
import {RouterModule} from '@angular/router';
import {VerticalBarChartComponent} from './vertical-bar-chart/vertical-bar-chart.component';
import {BarChartModule, NgxChartsModule} from '@swimlane/ngx-charts';

@NgModule({
    imports: [IonicModule, CommonModule, RouterModule, NgxChartsModule,],
    exports: [
        HeadersComponent,
        VerticalBarChartComponent
    ],
    declarations: [HeadersComponent, VerticalBarChartComponent],
    providers: [
        DatePipe,
    ],
})
export class ComponentsModule {
}
