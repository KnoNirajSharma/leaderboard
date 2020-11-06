import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { LabeledNumberCircleComponent } from './labeled-number-circle.component';

describe('LabeledNumberCircleComponent', () => {
  let component: LabeledNumberCircleComponent;
  let fixture: ComponentFixture<LabeledNumberCircleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LabeledNumberCircleComponent ],
      imports: [IonicModule.forRoot()],
    }).compileComponents();

    fixture = TestBed.createComponent(LabeledNumberCircleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
