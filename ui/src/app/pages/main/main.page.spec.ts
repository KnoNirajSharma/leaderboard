import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';
import { MainPage } from './main.page';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { EmployeeActivityService } from '../../services/employee-activity.service';
import {of } from 'rxjs';
import { TableComponent } from '../../components/table/table.component';
import { EmployeeFilterPipe } from '../../pipe/employee-filter.pipe';
import { ReactiveFormsModule } from '@angular/forms';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { ComponentsModule } from '../../components/components.module';
import { ReputationModel } from '../../models/reputation.model';
import { AngularFireModule } from '@angular/fire';
import { environment } from '../../../environments/environment';
import { AngularFirestoreModule } from '@angular/fire/firestore';
import { AngularFireAuthModule } from '@angular/fire/auth';
import { CustomPipesModule } from '../../pipe/custom-pipes.module';
import { ScoringTableModel } from '../../models/scoring-table.model';
import {ElementRef} from '@angular/core';

describe('MainPage', () => {
  let component: MainPage;
  let fixture: ComponentFixture<MainPage>;
  let mockEmployeeService: EmployeeActivityService;
  const dummyReputationData: ReputationModel = {
    blogs: { monthly: 2, allTime: 3 },
    knolx: { monthly: 2, allTime: 3 },
    webinars: { monthly: 2, allTime: 3 },
    techhubTemplates: { monthly: 2, allTime: 3 },
    osContributions: { monthly: 2, allTime: 3 },
    conferences: { monthly: 2, allTime: 3 },
    books: { monthly: 2, allTime: 3 },
    researchPapers: { monthly: 2, allTime: 3 },
    reputation: [
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
      }
    ]
  };
  const mockScoringData: ScoringTableModel = {
    blog: {points: 5, pointsMultiplier: 1},
    knolx: {points: 5, pointsMultiplier: 1},
    webinar: {points: 5, pointsMultiplier: 1},
    techhubTemplate: {points: 5, pointsMultiplier: 1},
    osContribution: {points: 5, pointsMultiplier: 1},
    conference: {points: 5, pointsMultiplier: 1},
    book: {points: 5, pointsMultiplier: 1},
    researchPaper: {points: 5, pointsMultiplier: 1},
  };
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [MainPage],
      imports: [
        HttpClientTestingModule,
        IonicModule.forRoot(),
        RouterTestingModule,
        ReactiveFormsModule,
        NgxDatatableModule,
        ComponentsModule,
        AngularFireModule.initializeApp(environment.firebaseConfig, 'angular-auth-firebase'),
        AngularFirestoreModule,
        AngularFireAuthModule,
        CustomPipesModule
      ],
      providers: [EmployeeFilterPipe]
    }).compileComponents();

    fixture = TestBed.createComponent(MainPage);
    component = fixture.componentInstance;
    mockEmployeeService = TestBed.get(EmployeeActivityService);
  }));

  it('should call getReputationData and scoringInfoData', () => {
    spyOn(component, 'getReputationData');
    spyOn(component, 'getScoringInfoData');
    component.ngOnInit();
    expect(component.getScoringInfoData).toHaveBeenCalled();
    expect(component.getReputationData).toHaveBeenCalled();
  });

  it('should return the authorData as per api call', () => {
    spyOn(component, 'setAllKnolderData');
    spyOn(mockEmployeeService, 'getData').and.returnValue(of(dummyReputationData));
    component.getReputationData();
    expect(component.reputation).toEqual(dummyReputationData);
  });

  it('should return the scoring info data as per api call', () => {
    spyOn(component, 'getScoringInfoKeys').and.returnValue(['blog', 'knolx']);
    spyOn(component, 'getNumberOfScoresBoosted').and.returnValue(3);
    spyOn(mockEmployeeService, 'getScoringInfoData').and.returnValue(of(mockScoringData));
    component.getScoringInfoData();
    expect(component.scoringInfoData).toEqual(mockScoringData);
    expect(component.scoringInfoKeys).toEqual(['blog', 'knolx']);
    expect(component.boostedScoreCount).toEqual(3);
  });

  it('should call setKnolderList, setKnoldusReputationKeys and setInitialFilteredList', () => {
    spyOn(component, 'setKnoldersList');
    spyOn(component, 'setKnoldusStatsReputationKeys');
    component.setAllKnolderData();
    expect(component.setKnoldersList).toHaveBeenCalled();
    expect(component.setKnoldusStatsReputationKeys).toHaveBeenCalled();
  });

  it('should construct array of reputation keys excepts reputation', () => {
    component.reputation = {...dummyReputationData};
    component.setKnoldusStatsReputationKeys();
    expect(component.knoldusStatsReputationKeys[0]).toEqual('blogs');
    expect(component.knoldusStatsReputationKeys.indexOf('reputation')).toEqual(-1);
  });

  it('should construct array of keys from scoringTableModel', () => {
    component.scoringInfoData = {...mockScoringData};
    expect(component.getScoringInfoKeys()[0]).toEqual('blog');
  });

  it('should add topRanker equal to true if the index is less than 5', () => {
    component.reputation = {...dummyReputationData};
    component.setKnoldersList();
    expect(component.knoldersReputationList[1].topRanker).toEqual(true);
  });

  it('should not add topRanker parameter to knolder is index is 5 or greater', () => {
    component.reputation = {
      ...dummyReputationData,
      reputation: [
        {...dummyReputationData.reputation[0]},
        {...dummyReputationData.reputation[0]},
        {...dummyReputationData.reputation[0]},
        {...dummyReputationData.reputation[0]},
        {...dummyReputationData.reputation[0]},
        {...dummyReputationData.reputation[0]},
      ]
    };
    component.setKnoldersList();
    expect(component.knoldersReputationList[5].topRanker).toBeUndefined();
  });

  it('should get the number of scores boosted', ()  => {
    component.scoringInfoKeys = ['blog', 'knolx', 'webinar'];
    component.scoringInfoData = {
      ...mockScoringData,
      blog: {points: 5, pointsMultiplier: 2},
      knolx: {points: 20, pointsMultiplier: 2}
    };
    expect(component.getNumberOfScoresBoosted()).toEqual(2);
  });

  it('should set knoldusStatLegend x and y position', () => {
    component.knoldusStatsRef = new ElementRef<any>({offsetHeight: 80});
    component.mouseEnterOnKnoldusStatsHandler({offsetX: 30});
    expect(component.knoldusStatsLegendPosX).toEqual(30);
    expect(component.knoldusStatsLegendPosY).toEqual(80);
  });
});
