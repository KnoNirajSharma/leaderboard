import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';

import {MainPage} from './main.page';
import {RouterModule, Routes} from '@angular/router';
import {IonicModule} from '@ionic/angular';
import {HeadersComponent} from '../../components/headers/headers.component';

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
        RouterModule.forChild(routes),
        IonicModule
    ],
    declarations: [MainPage, HeadersComponent]
})
export class MainPageModule {
}
