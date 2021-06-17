import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import {CustomPipesModule} from '../../../pipe/custom-pipes.module';
import {LegendTooltipComponent} from './legend-tooltip.component';

describe('LegendTooltipComponent', () => {
  let component: LegendTooltipComponent;
  let fixture: ComponentFixture<LegendTooltipComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ LegendTooltipComponent ],
      imports: [CustomPipesModule],
    }).compileComponents();

    fixture = TestBed.createComponent(LegendTooltipComponent);
    component = fixture.componentInstance;
  }));

  it('should set legendKeys', () => {
    component.legendData = {legendOne: 'red', legendTwo: 'blue'};
    component.ngOnInit();
    expect(component.legendKeys[0]).toEqual('legendOne');
  });
});
