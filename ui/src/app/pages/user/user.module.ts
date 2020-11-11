import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {IonicModule} from '@ionic/angular';

import {ComponentsModule} from '../../components/components.module';
import {CustomPipesModule} from '../../pipe/custom-pipes.module';
import {MainPage} from './main/main.page';
import {UserPageRoutingModule} from './user-routing.module';
import {UserPage} from './user.page';

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        IonicModule,
        ComponentsModule,
        CustomPipesModule,
        UserPageRoutingModule,
    ],
    declarations: [UserPage, MainPage],
})
export class UserPageModule {
}
