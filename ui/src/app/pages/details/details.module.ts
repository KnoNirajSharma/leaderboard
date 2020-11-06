import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { IonicModule } from '@ionic/angular';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';

import { ComponentsModule } from '../../components/components.module';
import { ScoreDetailsComponent } from './components/score-details/score-details.component';
import { ScoreDrilldownComponent } from './components/score-drilldown/score-drilldown.component';
import { DetailsPage } from './details.page';

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
    IonicModule,
    BsDatepickerModule.forRoot(),
    RouterModule.forChild(routes),
    ReactiveFormsModule,
    ComponentsModule,
    NgxChartsModule,
  ],
  declarations: [DetailsPage, ScoreDrilldownComponent, ScoreDetailsComponent],
})
export class DetailsPageModule {
}
