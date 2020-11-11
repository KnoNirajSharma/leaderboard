import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';
import {IonicModule} from '@ionic/angular';

import {ComponentsModule} from '../../components/components.module';
import {UserPageRoutingModule} from './user-routing.module';
import {UserPage} from './user.page';

@NgModule({
    imports: [
        CommonModule,
        IonicModule,
        ComponentsModule,
        UserPageRoutingModule,
    ],
    declarations: [UserPage],
})
export class UserPageModule {
}
