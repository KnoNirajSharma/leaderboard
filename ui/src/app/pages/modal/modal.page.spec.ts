import {async, ComponentFixture, fakeAsync, TestBed, tick} from '@angular/core/testing';
import {IonicModule, ModalController, NavParams} from '@ionic/angular';

import {ModalPage} from './modal.page';
import {RouterTestingModule} from '@angular/router/testing';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {HeadersComponent} from '../../components/headers/headers.component';
import {TableComponent} from '../../components/table/table.component';
import {SidebarComponent} from '../../components/sidebar/sidebar.component';
import {AuthorModel} from '../../models/author.model';

describe('ModalPage', () => {
  let component: ModalPage;
  let fixture: ComponentFixture<ModalPage>;
  const dummyAuthorDetails: AuthorModel = {
    authorName: 'mark',
    score: 100,
    rank: 2,
    monthlyScore: 'N/A',
    monthlyRank: 'N/A',
    monthlyStreak: 'N/A'
  };
  const navParams = new NavParams(dummyAuthorDetails);

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ModalPage, HeadersComponent, TableComponent, SidebarComponent],
      imports: [HttpClientTestingModule, IonicModule.forRoot(), RouterTestingModule],
      providers: [
        {provide: NavParams, useValue: navParams},
        ModalController
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(ModalPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it ('should open when clicked `closeModal` button', fakeAsync( () => {
    spyOn(component, 'closeModal');
    const button = fixture.debugElement.nativeElement.querySelector('ion-button');
    fixture.detectChanges();
    button.click();
    tick();
    expect(component.closeModal).toBeTruthy('closeModal should now be true');
  }));

  it('should use injected data', () => {
    component.ngOnInit();
    expect(component.authorDetails).toEqual(dummyAuthorDetails);
  });
});
