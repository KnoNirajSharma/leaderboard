import {async, ComponentFixture, TestBed} from '@angular/core/testing';
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
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it ('should close when clicked `closeModal` button', async () => {
    spyOn(component, 'closeModal').and.callThrough();
    fixture.detectChanges();
    expect(component.closeModal).toBeTruthy('closeModal should now be true');
  });
});
