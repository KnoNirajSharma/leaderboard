import {async, ComponentFixture, fakeAsync, TestBed, tick} from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';
import { TabComponent } from './tab.component';
import {Component} from '@angular/core';
import {HeadersComponent} from '../headers/headers.component';

describe('TabComponent', () => {
  let component: TabComponent;
  let fixture: ComponentFixture<ParentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TabComponent, ParentComponent, ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(ParentComponent);
    component = fixture.debugElement.children[0].componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call toggleSidebar function', fakeAsync(() => {
    tick();
    component.select('monthly');
    expect(component.currentlySelected).toEqual('monthly');
  }));
});

@Component({
  selector: 'parent',
  template: '<app-tab [tabData]="tabData"></app-tab>'
})
class ParentComponent {
  tabData =  [
    {tabName: 'Overall', id: 'overall'},
    {tabName: 'Monthly', id: 'monthly'},
    {tabName: '3 month streak', id: 'streak'}
  ];
}
