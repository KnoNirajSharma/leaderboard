import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {AdminPage} from './admin.page';
import {UserManagementPage} from './user-management/user-management.page';

const routes: Routes = [
    {
        path: '',
        component: AdminPage,
        children: [
            {
                path: 'user-management',
                component: UserManagementPage,
            },
        ],
    },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
})
export class AdminPageRoutingModule {
}
