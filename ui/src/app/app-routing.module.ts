import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';

import {EmployeeActivityService} from './services/employee-activity.service';


const routes: Routes = [

  {
   path: '',
   pathMatch: 'full'
  },

  {
    path: '',
    loadChildren: './pages/main/main.module#MainPageModule',
    canActivate: [EmployeeActivityService],
  },
  {
    path: 'details',
    loadChildren: './pages/details/details.module#DetailsPageModule'
  }
];
@NgModule({
  imports: [
    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {}
