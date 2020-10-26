import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { TribeDetailsPage } from './tribe-details.page';

const routes: Routes = [
  {
    path: ':id',
    component: TribeDetailsPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class TribeDetailsPageRoutingModule {}
