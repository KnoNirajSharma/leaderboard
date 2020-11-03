import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CommonService {
  verticalBarChartConfigs =  { barPadding: 10, yAxisLabel: 'score' };
  chartColorScheme = {
    domain: ['#2C42A5', '#4CA52C', '#C7AD05', '#224A4B', '#0B8D84', '#2CA1A5', '#2F3640', '#4B0082']
  };
  numberOfItemsInHallOfFame = 10;

  get verticalBarChartPadding(): number {
    return this.verticalBarChartConfigs.barPadding;
  }

  get verticalBarChartYLabel(): string {
    return this.verticalBarChartConfigs.yAxisLabel;
  }

  get colorScheme(): { domain: string[]; } {
    return this.chartColorScheme;
  }

  get getNumberOfItemsInHallOfFame(): number {
    return this.numberOfItemsInHallOfFame;
  }
}
