import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { DetailsPage } from './details.page';
import {RouterModule, Routes} from '@angular/router';
import {ComponentsModule} from '../../components/components.module';
import {HeadersComponent} from '../../components/headers/headers.component';

const routes: Routes = [
  {
    path: '',
    component: DetailsPage
  }
];

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        IonicModule,
        RouterModule.forChild(routes),
        ComponentsModule,
    ],
  declarations: [DetailsPage]
})
export class DetailsPageModule {}
