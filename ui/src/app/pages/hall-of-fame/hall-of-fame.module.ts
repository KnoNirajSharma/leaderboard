import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import { HallOfFamePage } from './hall-of-fame.page';
import { RouterModule, Routes } from '@angular/router';
import { ComponentsModule } from '../../components/components.module';
import { ReverseListPipe } from '../../pipe/reverse-list.pipe';
import { CustomPipesModule } from '../../pipe/CustomPipes.module';


const routes: Routes = [
  {
    path: '',
    component: HallOfFamePage
  }
];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ComponentsModule,
    RouterModule.forChild(routes),
    CustomPipesModule
  ],
  declarations: [HallOfFamePage]
})
export class HallOfFamePageModule {}
