import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import {IonicModule} from '@ionic/angular';

import {CustomPipesModule} from '../../../pipe/custom-pipes.module';
import {BadgeDetailTableComponent} from './badge-detail-table.component';

describe('BadgeDetailTableComponent', () => {
  let component: BadgeDetailTableComponent;
  let fixture: ComponentFixture<BadgeDetailTableComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BadgeDetailTableComponent ],
      imports: [IonicModule.forRoot(), CustomPipesModule],
    }).compileComponents();

    fixture = TestBed.createComponent(BadgeDetailTableComponent);
    component = fixture.componentInstance;
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
