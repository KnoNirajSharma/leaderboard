import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import { RouterModule, Routes } from '@angular/router';
import { MainPage } from './main.page';
import { TableComponent } from '../../components/table/table.component';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { ComponentsModule } from '../../components/components.module';
import { CustomPipesModule } from '../../pipe/custom-pipes.module';

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
    NgxDatatableModule,
    ComponentsModule,
    CustomPipesModule
  ],
  declarations: [MainPage, TableComponent]
})
export class MainPageModule {
}
