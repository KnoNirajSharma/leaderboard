import {Component, Input, OnInit} from '@angular/core';
import {DatePipe} from '@angular/common';

@Component({
  selector: 'app-vertical-bar-chart',
  templateUrl: './vertical-bar-chart.component.html',
  styleUrls: ['./vertical-bar-chart.component.scss'],
})
export class VerticalBarChartComponent implements OnInit {
  @Input() inputResultList: {month: string, score: number}[] = [];
  public colorScheme = {
    domain: ['#1862c6']
  };
  result: {name: string, value: number}[] = [];
  refunds = [
    {
      name: 'Item-1',
      value: 2371
    }, {
      name: 'Item-2',
      value: 2071
    }, {
      name: 'Item-3',
      value: 2390
    }, {
      name: 'Item-4',
      value: 2701
    }, {
      name: 'Item-5',
      value: 3721
    }, {
      name: 'Item-6',
      value: 2791
    }, {
      name: 'Item-7',
      value: 1301
    }, {
      name: 'Item-8',
      value: 1001
    }, {
      name: 'Item-9',
      value: 1401
    }, {
      name: 'Item-10',
      value: 1801
    }, {
      name: 'Item-11',
      value: 1001
    }, {
      name: 'Item-12',
      value: 1891
    },
  ];

  constructor(private datePipe: DatePipe) { }

  ngOnInit() {
    this.inputResultList.map(obj => this.result.push({name: this.datePipe.transform(obj.month, 'MMM-yyyy'), value: obj.score}));
  }

}
