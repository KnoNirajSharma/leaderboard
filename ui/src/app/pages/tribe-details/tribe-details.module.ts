import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { TribeDetailsPageRoutingModule } from './tribe-details-routing.module';

import { TribeDetailsPage } from './tribe-details.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    TribeDetailsPageRoutingModule
  ],
  declarations: [TribeDetailsPage]
})
export class TribeDetailsPageModule {}
