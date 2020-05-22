import {async, ComponentFixture, fakeAsync, TestBed, tick} from '@angular/core/testing';
import {IonicModule, ModalController, NavParams} from '@ionic/angular';
import {ModalPage} from './modal.page';
import {RouterTestingModule} from '@angular/router/testing';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {HeadersComponent} from '../../components/headers/headers.component';
import {TableComponent} from '../../components/table/table.component';
import {SidebarComponent} from '../../components/sidebar/sidebar.component';

describe('ModalPage', () => {
  let component: ModalPage;
  let fixture: ComponentFixture<ModalPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ModalPage, HeadersComponent, TableComponent, SidebarComponent],
      imports: [HttpClientTestingModule, IonicModule.forRoot(), RouterTestingModule],
      providers: [ModalController]
    }).compileComponents();
    fixture = TestBed.createComponent(ModalPage);
    component = fixture.componentInstance;
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
});

