import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { IonicModule } from '@ionic/angular';

import { ComponentsModule } from '../../components/components.module';
import { CustomPipesModule } from '../../pipe/custom-pipes.module';
import { HallOfFamePage } from './hall-of-fame.page';


const routes: Routes = [
  {
    path: '',
    component: HallOfFamePage,
  },
];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ComponentsModule,
    RouterModule.forChild(routes),
    CustomPipesModule,
  ],
  declarations: [HallOfFamePage],
})
export class HallOfFamePageModule {}
