import {async, ComponentFixture, TestBed} from '@angular/core/testing';
import {IonicModule} from '@ionic/angular';
import {HallOfFamePage} from './hall-of-fame.page';
import {EmployeeActivityService} from '../../services/employee-activity.service';
import {LoadingControllerService} from '../../services/loading-controller.service ';
import {HallOfFameModel} from '../../models/hallOfFame.model';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {AngularFireModule} from '@angular/fire';
import {environment} from '../../../environments/environment';
import {AngularFirestoreModule} from '@angular/fire/firestore';
import {AngularFireAuthModule} from '@angular/fire/auth';
import {of, throwError} from 'rxjs';
import {ComponentsModule} from '../../components/components.module';
import {RouterTestingModule} from '@angular/router/testing';
import {CustomPipesModule} from '../../pipe/custom-pipes.module';

describe('HallOfFamePage', () => {
  let component: HallOfFamePage;
  let fixture: ComponentFixture<HallOfFamePage>;
  let mockEmployeeService: EmployeeActivityService;
  let loadingControllerService: LoadingControllerService;
  const mockHallOfFameData: HallOfFameModel[] = [
    {
      month: 'August',
      year: 2020,
      leaders: [
        {
          month: 'August',
          year: 2020,
          knolderId: 1,
          knolderName: 'Girish Chandra Bharti',
          monthlyRank: 1,
          monthlyScore: 100,
          allTimeRank: 4,
          allTimeScore: 2000,
        },
        {
          month: 'August',
          year: 2020,
          knolderId: 15,
          knolderName: 'Gaurav Kumar Shukla',
          monthlyRank: 5, monthlyScore: 100,
          allTimeRank: 4,
          allTimeScore: 2000,
        },
      ]
    },
    {
      month: 'September',
      year: 2020,
      leaders: [
        {
          month: 'September',
          year: 2020,
          knolderId: 1,
          knolderName: 'Girish Chandra Bharti',
          monthlyRank: 1,
          monthlyScore: 100,
          allTimeRank: 4,
          allTimeScore: 2000,
        },
        {
          month: 'September',
          year: 2020,
          knolderId: 15,
          knolderName: 'Gaurav Kumar Shukla',
          monthlyRank: 5, monthlyScore: 100,
          allTimeRank: 4,
          allTimeScore: 2000,
        },
      ]
    }
  ];

  beforeEach(async(() => {
      TestBed.configureTestingModule({
          declarations: [HallOfFamePage],
          imports: [
            HttpClientTestingModule,
            IonicModule.forRoot(),
            AngularFireModule.initializeApp(environment.firebaseConfig, 'angular-auth-firebase'),
            AngularFirestoreModule,
            AngularFireAuthModule,
            ComponentsModule,
            RouterTestingModule,
            CustomPipesModule
          ],
      }).compileComponents();

      fixture = TestBed.createComponent(HallOfFamePage);
      component = fixture.componentInstance;
      mockEmployeeService = TestBed.get(EmployeeActivityService);
      loadingControllerService = TestBed.get(LoadingControllerService);
  }));

  it('should return the hall of fame data as per api call', () => {
      spyOn(mockEmployeeService, 'getHallOfFameData').and.returnValue(of([...mockHallOfFameData]));
      component.ngOnInit();
      expect(component.hallOfFameLeaders).toEqual(mockHallOfFameData);
  });

  it('should dismiss loader when error occurred', () => {
      spyOn(loadingControllerService, 'dismiss');
      spyOn(component, 'setListIndexForPage');
      spyOn(mockEmployeeService, 'getHallOfFameData').and.returnValue(throwError({status: 404}));
      component.ngOnInit();
      expect(loadingControllerService.dismiss).toHaveBeenCalled();
  });

  it('should set start and end index of list for a page', () => {
    component.setListIndexForPage(1);
    expect(component.startIndexOfListForPage).toEqual(10);
    expect(component.lastIndexOfListForPage).toEqual(20);
  });
});
