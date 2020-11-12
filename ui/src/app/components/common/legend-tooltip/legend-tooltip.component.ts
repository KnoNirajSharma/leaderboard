import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-legend-tooltip',
  templateUrl: './legend-tooltip.component.html',
  styleUrls: ['./legend-tooltip.component.scss'],
})
export class LegendTooltipComponent implements OnInit {
  @Input() positionX: number;
  @Input() positionY: number;
  @Input() legendData;
  legendKeys: string[] = [];
  ngOnInit() {
    this.legendKeys = Object.keys(this.legendData);
  }
}
