import {async, ComponentFixture, fakeAsync, TestBed, tick} from '@angular/core/testing';
import {IonicModule, ModalController} from '@ionic/angular';
import {DrilldownModalPage} from './drilldown-modal.page';
import {RouterTestingModule} from '@angular/router/testing';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {HeadersComponent} from '../../components/headers/headers.component';
import {TableComponent} from '../../components/table/table.component';
import {of} from 'rxjs';
import {EmployeeActivityService} from '../../services/employee-activity.service';
import {KnolderDetailsModel} from '../../models/knolder-details.model';

describe('ModalPage', () => {
  let component: DrilldownModalPage;
  let fixture: ComponentFixture<DrilldownModalPage>;
  let mockEmployeeService: EmployeeActivityService;
  const dummyModalData: KnolderDetailsModel = {
    knolderName: 'mark',
    allTimeScore: 100,
    monthlyScore: 20,
    blogScore: 20,
    blogDetails: [
      {
        title: 'abc',
        date: '2020-05-06 13:34:09'
      }
    ]
  };

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [DrilldownModalPage, HeadersComponent, TableComponent],
      imports: [HttpClientTestingModule, IonicModule.forRoot(), RouterTestingModule],
      providers: [ModalController]
    }).compileComponents();
    fixture = TestBed.createComponent(DrilldownModalPage);
    component = fixture.componentInstance;
    mockEmployeeService = TestBed.get(EmployeeActivityService);
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it ('should open when clicked `closeModal` button', fakeAsync( () => {
    spyOn(component, 'closeModal');
    const button = fixture.debugElement.nativeElement.querySelector('ion-button');
    button.click();
    tick();
    expect(component.closeModal).toBeTruthy('closeModal should now be true');
  }));

  it('should return the modalData as per api call', () => {
    spyOn(mockEmployeeService, 'getDetails').and.returnValue(of(dummyModalData));
    component.ngOnInit();
    expect(component.modalData).toEqual(dummyModalData);
  });
});

