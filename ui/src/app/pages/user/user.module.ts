import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {IonicModule} from '@ionic/angular';
import {BsDatepickerModule} from 'ngx-bootstrap/datepicker';

import {ComponentsModule} from '../../components/components.module';
import {CustomPipesModule} from '../../pipe/custom-pipes.module';
import {AboutPage} from './about/about.page';
import {DetailsPage} from './details/details.page';
import {HallOfFamePage} from './hall-of-fame/hall-of-fame.page';
import {MainPage} from './main/main.page';
import {ReportIssuePage} from './report-issue/report-issue.page';
import {TribeDetailsPage} from './tribe-details/tribe-details.page';
import {TribesPage} from './tribes/tribes.page';
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
        BsDatepickerModule.forRoot(),
    ],
    declarations: [UserPage, ReportIssuePage, AboutPage, HallOfFamePage, MainPage, DetailsPage, TribesPage, TribeDetailsPage],
})
export class UserPageModule {
}
