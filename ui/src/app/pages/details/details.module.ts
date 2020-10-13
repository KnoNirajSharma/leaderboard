import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import { DetailsPage } from './details.page';
import { RouterModule, Routes } from '@angular/router';
import { ComponentsModule } from '../../components/components.module';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { ScoreDrilldownComponent } from './components/score-drilldown/score-drilldown.component';
import { ScoreDetailsComponent } from './components/score-details/score-details.component';

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
    NgxChartsModule
  ],
  declarations: [DetailsPage, ScoreDrilldownComponent, ScoreDetailsComponent]
})
export class DetailsPageModule {
}
