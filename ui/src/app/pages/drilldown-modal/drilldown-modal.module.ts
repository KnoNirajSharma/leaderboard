import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';

import {IonicModule} from '@ionic/angular';

import {DrilldownModalPage} from './drilldown-modal.page';
import {RouterModule, Routes} from '@angular/router';
import {MainPageModule} from '../main/main.module';

const routes: Routes = [
    {
        path: '',
        component: DrilldownModalPage
    }
];

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        IonicModule,
        RouterModule.forChild(routes),
        MainPageModule
    ],
    declarations: [DrilldownModalPage]
})
export class DrilldownModalPageModule {
}
