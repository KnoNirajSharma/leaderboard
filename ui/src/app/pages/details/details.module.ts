import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { DetailsPage } from './details.page';
import {RouterModule, Routes} from '@angular/router';
import {IonicModule} from '@ionic/angular';
import {HeadersComponent} from '../../components/headers/headers.component';

const routes: Routes = [
  {
    path: '',
    component: DetailsPage,
  },
];

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        RouterModule.forChild(routes),
        IonicModule
    ],
    declarations: [DetailsPage, HeadersComponent]
})
export class DetailsPageModule {}
