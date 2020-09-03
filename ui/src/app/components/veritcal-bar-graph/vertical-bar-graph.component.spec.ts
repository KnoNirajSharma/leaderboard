import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { Component } from '@angular/core';
import { IonicModule } from '@ionic/angular';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { VerticalBarGraphComponent } from './vertical-bar-graph.component';
import {TrendsModel} from '../../models/trends.model';

describe('VeritcalBarGraphComponent', () => {
  let component: VerticalBarGraphComponent;
  let fixture: ComponentFixture<ParentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        VerticalBarGraphComponent,
        ParentComponent
      ],
      imports: [
        IonicModule.forRoot(),
        NgxChartsModule,
        BrowserAnimationsModule
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(ParentComponent);
    component = fixture.debugElement.children[0].componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

@Component({
  selector: 'parent',
  template: '<app-vertical-bar-graph [inputResult]="dummyTrendsData"></app-vertical-bar-graph>'
})
class ParentComponent {
  dummyTrendsData: TrendsModel[] = [
    {
      month: 'JUNE',
      year: 2020,
      blogScore: 30,
      knolxScore: 20,
      webinarScore: 34,
      techHubScore: 20,
      osContributionScore: 30,
      conferenceScore: 30,
      bookScore: 100,
      researchPaperScore: 0,
    },
    {
      month: 'JULY',
      year: 2020,
      blogScore: 30,
      knolxScore: 20,
      webinarScore: 34,
      techHubScore: 20,
      osContributionScore: 20,
      conferenceScore: 30,
      bookScore: 0,
      researchPaperScore: 50,
    }
  ];
}
