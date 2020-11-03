import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import { TribesPage } from './tribes.page';
import { RouterModule, Routes } from '@angular/router';
import { ComponentsModule } from '../../components/components.module';
import { TribeCardComponent } from './components/tribe-card/tribe-card.component';
import { TribeDetailsPage } from './tribe-details/tribe-details.page';
import { LabeledNumberCircleComponent } from './components/labeled-number-circle/labeled-number-circle.component';
import { CustomPipesModule } from '../../pipe/custom-pipes.module';

const routes: Routes = [
  {
    path: '',
    component: TribesPage
  },
  {
    path: ':id',
    component: TribeDetailsPage
  }
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
  declarations: [TribesPage, TribeCardComponent, TribeDetailsPage, LabeledNumberCircleComponent]
})
export class TribesPageModule {}
