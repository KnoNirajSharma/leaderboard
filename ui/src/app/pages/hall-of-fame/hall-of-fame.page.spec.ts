import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { HallOfFamePage } from './hall-of-fame.page';

describe('HallOfFamePage', () => {
  let component: HallOfFamePage;
  let fixture: ComponentFixture<HallOfFamePage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HallOfFamePage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(HallOfFamePage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
