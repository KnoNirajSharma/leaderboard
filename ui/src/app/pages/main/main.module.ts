import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {IonicModule} from '@ionic/angular';
import {RouterModule, Routes} from '@angular/router';
import {MainPage} from './main.page';
import {CardComponent} from '../../components/card/card.component';
import {HeadersComponent} from '../../components/headers/headers.component';
import {SidebarComponent} from '../../components/sidebar/sidebar.component';
import {TableComponent} from '../../components/table/table.component';
import {TabComponent} from '../../components/tab/tab.component';

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
        IonicModule,
        RouterModule.forChild(routes),
    ],
    declarations: [MainPage, CardComponent, HeadersComponent, TableComponent, SidebarComponent,
        TabComponent],
})
export class MainPageModule {
}
