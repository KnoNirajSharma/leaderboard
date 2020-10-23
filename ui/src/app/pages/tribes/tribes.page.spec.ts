import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';
import { TribesPage } from './tribes.page';
import { ComponentsModule } from '../../components/components.module';
import { TribesSummeryModel } from '../../models/tribes-summery.model';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { AngularFireModule } from '@angular/fire';
import { environment } from '../../../environments/environment';
import { AngularFirestoreModule } from '@angular/fire/firestore';
import { AngularFireAuthModule } from '@angular/fire/auth';
import { EmployeeActivityService } from '../../services/employee-activity.service';
import { of } from 'rxjs';
import { RouterTestingModule } from '@angular/router/testing';

describe('TribesPage', () => {
  let component: TribesPage;
  let fixture: ComponentFixture<TribesPage>;
  let mockEmployeeService: EmployeeActivityService;
  const mockAllTrendSummeryData: TribesSummeryModel[] = [
    {
      id: 'scala',
      name: 'Scala',
      allTimeScore: 3000,
      monthlyScore: 200,
      memberAvg: 40,
      memberCount: 50
    },
    {
      id: 'dev-ops',
      name: 'DevOps',
      allTimeScore: 3000,
      monthlyScore: 200,
      memberAvg: 40,
      memberCount: 50
    },
  ];

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TribesPage ],
      imports: [
        IonicModule.forRoot(),
        ComponentsModule,
        HttpClientTestingModule,
        IonicModule.forRoot(),
        AngularFireModule.initializeApp(environment.firebaseConfig, 'angular-auth-firebase'),
        AngularFirestoreModule,
        AngularFireAuthModule,
        RouterTestingModule,
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(TribesPage);
    component = fixture.componentInstance;
    mockEmployeeService = TestBed.get(EmployeeActivityService);
  }));

  it('should return the all tribes summery data as per api call', () => {
    spyOn(mockEmployeeService, 'getAllTribesData').and.returnValue(of([...mockAllTrendSummeryData]));
    component.ngOnInit();
    expect(component.tribesList).toEqual(mockAllTrendSummeryData);
  });
});
