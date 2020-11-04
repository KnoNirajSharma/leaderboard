import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { IonicModule } from '@ionic/angular';
import { NgxChartsModule } from '@swimlane/ngx-charts';

import { TrendsModel } from '../../models/trends.model';
import { CommonService } from '../../services/common.service';
import { VerticalBarGraphComponent } from './vertical-bar-graph.component';

describe('VeritcalBarGraphComponent', () => {
  let component: VerticalBarGraphComponent;
  let fixture: ComponentFixture<VerticalBarGraphComponent>;
  let commonService: CommonService;
  const mockTrendsData: TrendsModel[] = [
    {
      month: 'JUNE',
      year: 2020,
      blogScore: 30,
      knolxScore: 20,
      webinarScore: 34,
      techHubScore: 20,
      osContributionScore: 30,
      conferenceScore: 30,
      bookScore: 100,
      researchPaperScore: 0,
    },
    {
      month: 'JULY',
      year: 2020,
      blogScore: 40,
      knolxScore: 20,
      webinarScore: 34,
      techHubScore: 20,
      osContributionScore: 20,
      conferenceScore: 30,
      bookScore: 0,
      researchPaperScore: 50,
    },
  ];

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        VerticalBarGraphComponent,
      ],
      imports: [
        IonicModule.forRoot(),
        NgxChartsModule,
        BrowserAnimationsModule,
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(VerticalBarGraphComponent);
    component = fixture.componentInstance;
    commonService = TestBed.get(CommonService);
  }));

  it('result array values should be equal to inputResult values', () => {
    spyOn(component, 'setBarGraphConfigs');
    component.trendsData = [...mockTrendsData];
    component.ngOnChanges();
    expect(component.result[0].series[0].value).toEqual(component.trendsData[1].blogScore);
    expect(component.setBarGraphConfigs).toHaveBeenCalled();
  });

  it('should set chart config values', () => {
    spyOnProperty(commonService, 'colorScheme').and.returnValue({domain: ['blue']});
    spyOnProperty(commonService, 'verticalBarChartYLabel').and.returnValue('scores');
    spyOnProperty(commonService, 'verticalBarChartPadding').and.returnValue(1);
    component.setBarGraphConfigs();
    expect(component.colorScheme.domain[0]).toEqual('blue');
    expect(component.yAxisLabel).toEqual('scores');
    expect(component.barPadding).toEqual(1);
  });
});
