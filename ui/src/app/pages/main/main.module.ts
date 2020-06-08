import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {IonicModule} from '@ionic/angular';
import {RouterModule, Routes} from '@angular/router';
import {MainPage} from './main.page';
import {CardComponent} from '../../components/card/card.component';
import {HeadersComponent} from '../../components/headers/headers.component';
import {TableComponent} from '../../components/table/table.component';
import {EmployeeFilterPipe} from '../../pipe/employee-filter.pipe';
import {NgxDatatableModule} from '@swimlane/ngx-datatable';
import {Sort} from '@angular/material/sort';

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
    ],
    declarations: [MainPage, CardComponent, HeadersComponent, TableComponent, EmployeeFilterPipe],
    exports: [
        HeadersComponent
    ]
})
export class MainPageModule {
}
