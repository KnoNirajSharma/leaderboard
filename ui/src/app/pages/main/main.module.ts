import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { IonicModule } from '@ionic/angular';

import { ComponentsModule } from '../../components/components.module';
import { CustomPipesModule } from '../../pipe/custom-pipes.module';
import { MainPage } from './main.page';

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
    ReactiveFormsModule,
    IonicModule,
    RouterModule.forChild(routes),
    ComponentsModule,
    CustomPipesModule,
  ],
  declarations: [MainPage],
})
export class MainPageModule {
}
