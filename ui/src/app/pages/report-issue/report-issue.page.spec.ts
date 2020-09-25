import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { ReportIssuePage } from './report-issue.page';

describe('ReportIssuePage', () => {
  let component: ReportIssuePage;
  let fixture: ComponentFixture<ReportIssuePage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReportIssuePage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(ReportIssuePage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
