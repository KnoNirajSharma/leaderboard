import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {IonicModule} from '@ionic/angular';

import {ComponentsModule} from '../../components/components.module';
import {AdminPageRoutingModule} from './admin-routing.module';
import {AdminPage} from './admin.page';
import {UserManagementPage} from './user-management/user-management.page';

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        IonicModule,
        AdminPageRoutingModule,
        ComponentsModule,
        ReactiveFormsModule,
    ],
    declarations: [AdminPage, UserManagementPage],
})
export class AdminPageModule {
}
