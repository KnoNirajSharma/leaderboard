import {Component, Input, OnInit} from '@angular/core';
import {AuthorModel} from '../../models/author.model';
import {DrilldownModalPage} from '../../pages/drilldown-modal/drilldown-modal.page';
import {ModalController} from '@ionic/angular';

@Component({
    selector: 'app-table',
    templateUrl: './table.component.html',
    styleUrls: ['./table.component.scss'],
})

export class TableComponent implements OnInit {
    @Input() tableRows: AuthorModel[];
    @Input() dataKeys: string[];
    columns = [{ name: 'Name', prop: 'knolderName'}, { name: 'Overall Score', prop: 'allTimeScore'},
        { name: 'Overall Rank', prop: 'allTimeRank'}, { name: 'Monthly Score', prop: 'monthlyScore'},
        { name: 'Monthly Rank', prop: 'monthlyScore'}, { name: '3 Month Streak', prop: 'quarterlyStreak', sortable: false}];

    constructor(private modalController: ModalController) {
    }

    ngOnInit() {
    }

    async presentModal(id: number) {
        const modal = await this.modalController.create({
            component: DrilldownModalPage,
            componentProps: {
                id
            }
        });
        return await modal.present();
    }
}
