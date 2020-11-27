import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';
import {ReactiveFormsModule} from '@angular/forms';
import {RouterModule} from '@angular/router';
import {IonicModule} from '@ionic/angular';
import {NgxChartsModule} from '@swimlane/ngx-charts';
import {NgxDatatableModule} from '@swimlane/ngx-datatable';

import {CustomPipesModule} from '../pipe/custom-pipes.module';
import {AddUserFormComponent} from './admin/add-user-form/add-user-form.component';
import {UserManagementTableRowComponent} from './admin/user-management-table/user-management-table-row/user-management-table-row.component';
import {UserManagementTableComponent} from './admin/user-management-table/user-management-table.component';
import {DropdownMenuListComponent} from './common/dropdown-menu-list/dropdown-menu-list.component';
import {DropdownWrapperComponent} from './common/dropdown-wrapper/dropdown-wrapper.component';
import {HeadersComponent} from './common/headers/headers.component';
import {LegendTooltipComponent} from './common/legend-tooltip/legend-tooltip.component';
import {ListPaginatorComponent} from './common/list-paginator/list-paginator.component';
import {MenuBoxComponent} from './common/menu-box/menu-box.component';
import {PieChartComponent} from './common/pie-chart/pie-chart.component';
import {TableComponent} from './common/table/table.component';
import {VerticalBarGraphComponent} from './common/veritcal-bar-graph/vertical-bar-graph.component';
import {BadgeDetailTableComponent} from './knolder-details/badge-detail-table/badge-detail-table.component';
import {MedalTallyComponent} from './knolder-details/medal-tally/medal-tally.component';
import {ScoreDetailsComponent} from './knolder-details/score-details/score-details.component';
import {ScoreDrilldownComponent} from './knolder-details/score-drilldown/score-drilldown.component';
import {LabeledNumberCircleComponent} from './tribes/labeled-number-circle/labeled-number-circle.component';
import {TribeCardComponent} from './tribes/tribe-card/tribe-card.component';


@NgModule({
    imports: [
        IonicModule,
        CommonModule,
        RouterModule,
        NgxChartsModule,
        CustomPipesModule,
        NgxDatatableModule,
        ReactiveFormsModule,
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
        TribeCardComponent,
        LabeledNumberCircleComponent,
        UserManagementTableRowComponent,
        UserManagementTableComponent,
        DropdownWrapperComponent,
        DropdownMenuListComponent,
        AddUserFormComponent,
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
        TribeCardComponent,
        LabeledNumberCircleComponent,
        UserManagementTableRowComponent,
        UserManagementTableComponent,
        DropdownWrapperComponent,
        DropdownMenuListComponent,
        AddUserFormComponent,
    ],
})
export class ComponentsModule {
}
