import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { IonicModule } from '@ionic/angular';

import { ComponentsModule } from '../../components/components.module';
import { CustomPipesModule } from '../../pipe/custom-pipes.module';
import { LabeledNumberCircleComponent } from './components/labeled-number-circle/labeled-number-circle.component';
import { TribeCardComponent } from './components/tribe-card/tribe-card.component';
import { TribeDetailsPage } from './tribe-details/tribe-details.page';
import { TribesPage } from './tribes.page';

const routes: Routes = [
  {
    path: '',
    component: TribesPage,
  },
  {
    path: ':id',
    component: TribeDetailsPage,
  },
];

@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
    IonicModule,
    RouterModule.forChild(routes),
    ComponentsModule,
    CustomPipesModule,
  ],
  declarations: [TribesPage, TribeCardComponent, TribeDetailsPage, LabeledNumberCircleComponent],
})
export class TribesPageModule {}
