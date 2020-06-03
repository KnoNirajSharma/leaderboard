import {Component, Input, OnInit} from '@angular/core';
import {ModalController} from '@ionic/angular';
import {KnolderDetailsModel} from '../../models/knolder-details.model';
import {EmployeeActivityService} from '../../services/employee-activity.service';
import {TableHeaderModel} from '../../models/tableHeader.model';

@Component({
    selector: 'app-modal',
    templateUrl: './drilldown-modal.page.html',
    styleUrls: ['./drilldown-modal.page.scss'],
})
export class DrilldownModalPage implements OnInit {
    @Input() id;
    title = 'Details';
    modalData: KnolderDetailsModel;
    modalHeading: TableHeaderModel[];
    blogDetails: TableHeaderModel[];

    constructor(private service: EmployeeActivityService, private modalController: ModalController) {
    }

    ngOnInit() {
        this.service.getDetails(this.id)
            .subscribe((data: KnolderDetailsModel) => {
                this.modalData = data;
            });
        this.modalHeading = [
            {title: 'Score Drilldown'},
            {title: 'Current Month'},
            {title: 'Overall Score'},
            {title: 'Monthly Score'},
            {title: 'Blog Score'}
        ];

        this.blogDetails = [
            {title: 'Blog Title'},
            {title: 'Blog Date/Time'}
        ];
    }

    async closeModal() {
        const onClosedData = '';
        await this.modalController.dismiss(onClosedData);
    }
}
