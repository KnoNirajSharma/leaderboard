import {HttpClientTestingModule} from '@angular/common/http/testing';
import {async, ComponentFixture, TestBed} from '@angular/core/testing';
import {ReactiveFormsModule} from '@angular/forms';
import {ActivatedRoute} from '@angular/router';
import {RouterTestingModule} from '@angular/router/testing';
import {IonicModule} from '@ionic/angular';
import {of} from 'rxjs';

import {ComponentsModule} from '../../../components/components.module';
import {TribeDetailsModel} from '../../../models/tribe-details-page/tribe-details.model';
import {CustomPipesModule} from '../../../pipe/custom-pipes.module';
import {EmployeeActivityService} from '../../../services/employee-activity/employee-activity.service';
import {TribeDetailsPage} from './tribe-details.page';

describe('TribeDetailsPage', () => {
    let component: TribeDetailsPage;
    let fixture: ComponentFixture<TribeDetailsPage>;
    let mockEmployeeActivitySerivce: EmployeeActivityService;
    const mockTribeDetailsData: TribeDetailsModel = {
        name: 'DevOps',
        tribeSummery: [
            {name: 'all time score', value: 2000},
            {name: 'monthly score', value: 200},
            {name: 'Per member score', value: 20},
        ],
        allTimeScoreBreakdown: [
            {contributionType: 'Blog', contributionScore: 45},
            {contributionType: 'Knolx', contributionScore: 4},
            {contributionType: 'webinar', contributionScore: 70},
        ],
        trends: [
            {
                month: 'JUNE',
                year: 2020,
                blogScore: 30,
                knolxScore: 20,
                webinarScore: 34,
                techHubScore: 20,
                osContributionScore: 30,
                conferenceScore: 100,
                bookScore: 100,
                researchPaperScore: 0,
                meetUpScore: 0,
            },
            {
                month: 'JULY',
                year: 2020,
                blogScore: 30,
                knolxScore: 20,
                webinarScore: 34,
                techHubScore: 20,
                osContributionScore: 20,
                conferenceScore: 0,
                bookScore: 0,
                researchPaperScore: 100,
                meetUpScore: 0,
            },
        ],
        memberReputations: [
            {
                knolderId: 1,
                knolderName: 'mark',
                allTimeScore: 10,
                allTimeRank: 7,
                quarterlyStreak: '5-6-7',
                monthlyScore: 10,
                monthlyRank: 1,
            }, {
                knolderId: 2,
                knolderName: 'sam',
                allTimeScore: 20,
                allTimeRank: 6,
                quarterlyStreak: '5-6-8',
                monthlyScore: 10,
                monthlyRank: 1,
            },
        ],
    };

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [TribeDetailsPage],
            imports: [
                HttpClientTestingModule,
                IonicModule.forRoot(),
                RouterTestingModule,
                ComponentsModule,
                CustomPipesModule,
                ReactiveFormsModule,
            ],
            providers: [
                {
                    provide: ActivatedRoute,
                    useValue: {
                        queryParams: of({
                            id: 'dev-ops',
                        }),
                    },
                }],
        }).compileComponents();

        fixture = TestBed.createComponent(TribeDetailsPage);
        component = fixture.componentInstance;
        mockEmployeeActivitySerivce = TestBed.get(EmployeeActivityService);
    }));

    it('should get tribe id from params and call getTribeDetails', () => {
        spyOn(component, 'getTribeDetails');
        component.ngOnInit();
        expect(component.tribeId).toEqual('dev-ops');
        expect(component.getTribeDetails).toHaveBeenCalled();
    });

    it('should get tribe detail data from API', () => {
        spyOn(mockEmployeeActivitySerivce, 'getTribeDetails').and.returnValue(of({...mockTribeDetailsData}));
        component.getTribeDetails('dev-ops');
        expect(component.tribeDetails).toEqual(mockTribeDetailsData);
    });
});
