import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { HallOfFamePage } from './hall-of-fame.page';

const routes: Routes = [
  {
    path: '',
    component: HallOfFamePage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class HallOfFamePageRoutingModule {}
