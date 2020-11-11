import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {DetailsPage} from './details/details.page';
import {MainPage} from './main/main.page';
import {UserPage} from './user.page';

const routes: Routes = [
    {
        path: '',
        component: UserPage,
        children: [
            {
                path: '',
                component: MainPage,
            },
            {
                path: 'details',
                component: DetailsPage,
            },
        ],
    },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
})
export class UserPageRoutingModule {
}
