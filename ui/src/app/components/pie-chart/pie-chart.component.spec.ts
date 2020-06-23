import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { PieChartComponent } from './pie-chart.component';
import {Component} from '@angular/core';
import {KnolderDetailsModel} from '../../models/knolder-details.model';
import {NgxChartsModule} from '@swimlane/ngx-charts';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

describe('PieChartComponent', () => {
  let component: PieChartComponent;
  let fixture: ComponentFixture<ParentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PieChartComponent, ParentComponent ],
      imports: [IonicModule.forRoot(),
        NgxChartsModule,
      BrowserAnimationsModule]
    }).compileComponents();

    fixture = TestBed.createComponent(ParentComponent);
    component = fixture.debugElement.children[0].componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('result array values should be equal to inputResult values', () => {
    expect(component.result).toEqual([{name: 'Blog', value: 20}]);
  });
});

@Component({
  selector: 'parent',
  template: '<app-pie-chart [inputResult]="dummyKnolderDetails.scoreBreakDown" [colorScheme]="pieChartColor"></app-pie-chart>'
})
class ParentComponent {
  dummyKnolderDetails: KnolderDetailsModel = {
    knolderName: 'Muskan Gupta',
    score: 20,
    scoreBreakDown: [
      {
        contributionType: 'Blog',
        contributionCount: 4,
        contributionScore: 20,
        contributionDetails: [
          {
            title: 'Serialization in Lagom',
            date: '2020-05-06 14:16:23'
          }
        ]
      }
    ]
  };

  pieChartColor = {
    domain: ['#1862c6', '#3380e6']
  };
}
