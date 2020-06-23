import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';
import {IonicModule} from '@ionic/angular';
import {HeadersComponent} from './headers/headers.component';
import {RouterModule} from '@angular/router';
import {NgxChartsModule} from '@swimlane/ngx-charts';
import {PieChartComponent} from './pie-chart/pie-chart.component';

@NgModule({
    imports: [IonicModule, CommonModule, RouterModule, NgxChartsModule ],
    exports: [
        HeadersComponent,
        PieChartComponent
    ],
    declarations: [HeadersComponent, PieChartComponent],
})
export class ComponentsModule {
}
