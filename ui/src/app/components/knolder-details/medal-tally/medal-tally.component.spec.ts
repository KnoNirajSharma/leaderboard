import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { MedalTallyComponent } from './medal-tally.component';

describe('MedalTallyComponent', () => {
  let component: MedalTallyComponent;
  let fixture: ComponentFixture<MedalTallyComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ MedalTallyComponent ],
      imports: [IonicModule.forRoot()],
    }).compileComponents();

    fixture = TestBed.createComponent(MedalTallyComponent);
    component = fixture.componentInstance;
  }));

  it('should set medalTallyKeys according to medalTally object', () => {
    component.medalTally = {
      gold: {count: 1, imgUrl: './assets/icon/gold-medal.svg'},
      silver: {count: 0, imgUrl: './assets/icon/silver-medal.svg'},
      bronze: {count: 2, imgUrl: './assets/icon/bronze-medal.svg'},
    };
    component.ngOnInit();
    expect(component.medalTallyKeys[0]).toEqual('gold');
  });
});
