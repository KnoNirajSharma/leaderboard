import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { DetailsPage } from './details.page';
import {RouterModule, Routes} from '@angular/router';
import {ComponentsModule} from '../../components/components.module';
import {HeadersComponent} from '../../components/headers/headers.component';
import {BsDatepickerModule} from 'ngx-bootstrap/datepicker';

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
    BsDatepickerModule.forRoot(),
    RouterModule.forChild(routes),
    ReactiveFormsModule,
      ComponentsModule,
  ],
  declarations: [DetailsPage]
})
export class DetailsPageModule {}
