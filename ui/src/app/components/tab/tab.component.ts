import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {TabDataModel} from '../../models/tabData.model';

@Component({
    selector: 'app-tab',
    templateUrl: './tab.component.html',
    styleUrls: ['./tab.component.scss'],
})
export class TabComponent implements OnInit {
    @Input() tabData: TabDataModel[];
    @Input() selected: string;
    @Output() showTable = new EventEmitter<string>();
    currentlySelected: string;

    constructor() {
    }

    ngOnInit() {
    }

    select(value) {
        if (this.currentlySelected !== value) {
            this.currentlySelected = value;
            const tabs = document.getElementsByClassName('tab');
            for (let index = 0; index < tabs.length; index++) {
                tabs.item(index).classList.remove('selected');
            }
            document.getElementById(value).classList.add('selected');
            this.showTable.emit(value);
        }
    }
}
