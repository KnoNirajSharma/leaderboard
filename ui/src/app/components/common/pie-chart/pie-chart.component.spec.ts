import {async, ComponentFixture, TestBed} from '@angular/core/testing';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {IonicModule} from '@ionic/angular';
import {NgxChartsModule} from '@swimlane/ngx-charts';

import {KnolderDetailsModel} from '../../../models/knolder-details.model';
import {CommonService} from '../../../services/common/common.service';
import {PieChartComponent} from './pie-chart.component';

describe('PieChartComponent', () => {
    let component: PieChartComponent;
    let fixture: ComponentFixture<PieChartComponent>;
    let commonService: CommonService;
    const mockKnolderDetails: KnolderDetailsModel = {
        knolderName: 'Muskan Gupta',
        score: 20,
        scoreBreakDown: [
            {
                contributionType: 'Blog',
                contributionCount: 4,
                contributionScore: 20,
                contributionDetails: [
                    {
                        title: 'Serialization in Lagom',
                        date: '2020-05-06 14:16:23',
                    },
                ],
            },
        ],
    };

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [PieChartComponent],
            imports: [
                IonicModule.forRoot(),
                NgxChartsModule,
                BrowserAnimationsModule,
            ],
        }).compileComponents();

        fixture = TestBed.createComponent(PieChartComponent);
        component = fixture.componentInstance;
        commonService = TestBed.get(CommonService);
    }));

    it('result array values should be equal to inputResult values', () => {
        spyOnProperty(commonService, 'colorScheme', 'get').and.returnValue({domain: ['blue']});
        component.inputResult = [...mockKnolderDetails.scoreBreakDown];
        component.ngOnChanges();
        expect(component.result).toEqual([{name: 'Blog', value: 20}]);
        expect(component.colorScheme.domain[0]).toEqual('blue');
    });
});
