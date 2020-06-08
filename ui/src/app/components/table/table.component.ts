import {Component, Input, OnInit} from '@angular/core';
import {AuthorModel} from '../../models/author.model';
import {TableHeaderModel} from '../../models/tableHeader.model';
import {DrilldownModalPage} from '../../pages/drilldown-modal/drilldown-modal.page';
import {ModalController} from '@ionic/angular';
import {TranslateService} from '@ngx-translate/core';
import {EmployeeActivityService} from '../../services/employee-activity.service';
import {TableColumn} from '@swimlane/ngx-datatable';

@Component({
    selector: 'app-table',
    templateUrl: './table.component.html',
    styleUrls: ['./table.component.scss'],
})

export class TableComponent implements OnInit {
    @Input() tableHeaders: TableColumn[];
    @Input() tableRows: AuthorModel[];
    @Input() dataKeys: string[];

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
