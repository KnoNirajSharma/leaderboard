import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {IonicModule} from '@ionic/angular';
import {NgxChartsModule} from '@swimlane/ngx-charts';
import {NgxDatatableModule} from '@swimlane/ngx-datatable';

import {CustomPipesModule} from '../pipe/custom-pipes.module';
import {BadgeDetailTableComponent} from './badge-detail-table/badge-detail-table.component';
import {HeadersComponent} from './headers/headers.component';
import {ScoreDetailsComponent} from './knolder-details/score-details/score-details.component';
import {ScoreDrilldownComponent} from './knolder-details/score-drilldown/score-drilldown.component';
import {LegendTooltipComponent} from './legend-tooltip/legend-tooltip.component';
import {ListPaginatorComponent} from './list-paginator/list-paginator.component';
import {MedalTallyComponent} from './medal-tally/medal-tally.component';
import {MenuBoxComponent} from './menu-box/menu-box.component';
import {PieChartComponent} from './pie-chart/pie-chart.component';
import {TableComponent} from './table/table.component';
import {VerticalBarGraphComponent} from './veritcal-bar-graph/vertical-bar-graph.component';

@NgModule({
    imports: [
        IonicModule,
        CommonModule,
        RouterModule,
        NgxChartsModule,
        CustomPipesModule,
        NgxDatatableModule,
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
        TableComponent,
        ScoreDetailsComponent,
        ScoreDrilldownComponent,
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
        TableComponent,
        ScoreDetailsComponent,
        ScoreDrilldownComponent,
    ],
})
export class ComponentsModule {
}
