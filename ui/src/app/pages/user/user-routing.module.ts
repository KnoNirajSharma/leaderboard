import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {AboutPage} from './about/about.page';
import {DetailsPage} from './details/details.page';
import {HallOfFamePage} from './hall-of-fame/hall-of-fame.page';
import {MainPage} from './main/main.page';
import {ReportIssuePage} from './report-issue/report-issue.page';
import {TribeDetailsPage} from './tribe-details/tribe-details.page';
import {TribesPage} from './tribes/tribes.page';
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
            {
                path: 'about',
                component: AboutPage,
            },
            {
                path: 'report-issue',
                component: ReportIssuePage,
            },
            {
                path: 'hall-of-fame',
                component: HallOfFamePage,
            },
            {
                path: 'tribes',
                component: TribesPage,
            },
            {
                path: 'tribes/:id',
                component: TribeDetailsPage,
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
