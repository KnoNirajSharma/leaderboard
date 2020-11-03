import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-labeled-number-circle',
  templateUrl: './labeled-number-circle.component.html',
  styleUrls: ['./labeled-number-circle.component.scss'],
})
export class LabeledNumberCircleComponent {
  @Input() displayValue: number;
  @Input() label: string;
}
