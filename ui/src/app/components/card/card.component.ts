import {Component, Input, OnInit} from '@angular/core';
import {CardDataModel} from '../../models/cardData.model';

@Component({
    selector: 'app-card',
    templateUrl: './card.component.html',
    styleUrls: ['./card.component.scss'],
})
export class CardComponent implements OnInit {
    @Input() cardData: CardDataModel;

    constructor() {
    }

    ngOnInit() {
    }
}
