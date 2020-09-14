import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { HallOfFamePage } from './hall-of-fame.page';
import {EmployeeActivityService} from '../../services/employee-activity.service';
import {LoadingControllerService} from '../../services/loading-controller.service ';
import {HallOfFameModel} from '../../models/hallOfFame.model';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {ReactiveFormsModule} from '@angular/forms';
import {NgxDatatableModule} from '@swimlane/ngx-datatable';
import {ComponentsModule} from '../../components/components.module';
import {AngularFireModule} from '@angular/fire';
import {environment} from '../../../environments/environment';
import {AngularFirestoreModule} from '@angular/fire/firestore';
import {AngularFireAuthModule} from '@angular/fire/auth';
import {of} from 'rxjs';

describe('HallOfFamePage', () => {
  let component: HallOfFamePage;
  let fixture: ComponentFixture<HallOfFamePage>;
  let mockEmployeeService: EmployeeActivityService;
  let loadingControllerService: LoadingControllerService;
  const mockHallOfFameData: HallOfFameModel[] = [
    { month: 'August',
      year: 2020,
      leaders: [
        { month: 'August',
          year: 2020,
          knolderId: 1,
          knolderName: 'Girish Chandra Bharti',
          monthlyRank: 1,
          monthlyScore: 100,
          allTimeRank: 4,
          allTimeScore: 2000,
        },
        { month: 'August',
          year: 2020,
          knolderId: 15,
          knolderName: 'Gaurav Kumar Shukla',
          monthlyRank: 5, monthlyScore: 100,
          allTimeRank: 4,
          allTimeScore: 2000,
        },
      ] }
  ];

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HallOfFamePage ],
      imports: [
        HttpClientTestingModule,
        IonicModule.forRoot(),
        RouterTestingModule,
        ReactiveFormsModule,
        NgxDatatableModule,
        ComponentsModule,
        AngularFireModule.initializeApp(environment.firebaseConfig, 'angular-auth-firebase'),
        AngularFirestoreModule,
        AngularFireAuthModule
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(HallOfFamePage);
    component = fixture.componentInstance;
    // fixture.detectChanges();
    mockEmployeeService = TestBed.get(EmployeeActivityService);
    loadingControllerService = TestBed.get(LoadingControllerService);
  }));

  it('should return the hall of fame data as per api call', () => {
    spyOn(mockEmployeeService, 'getHallOfFameData').and.returnValue(of(mockHallOfFameData));
    component.ngOnInit();
    expect(component.hallOfFameLeaders).toEqual(mockHallOfFameData);
  });
});
