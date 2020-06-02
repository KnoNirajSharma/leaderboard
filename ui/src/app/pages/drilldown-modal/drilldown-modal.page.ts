import {Component, Input, OnInit} from '@angular/core';
import {ModalController} from '@ionic/angular';
import {KnolderDetailsModel} from '../../models/knolder-details.model';
import {EmployeeActivityService} from '../../services/employee-activity.service';

@Component({
    selector: 'app-modal',
    templateUrl: './drilldown-modal.page.html',
    styleUrls: ['./drilldown-modal.page.scss'],
})
export class DrilldownModalPage implements OnInit {
    @Input() id;
    modalData: KnolderDetailsModel;

    constructor(private service: EmployeeActivityService, private modalController: ModalController) {
    }

    ngOnInit() {
        this.service.getDetails()
            .subscribe((data: KnolderDetailsModel) => {
                this.modalData = data;
            });
    }

    async closeModal() {
        const onClosedData = '';
        await this.modalController.dismiss(onClosedData);
    }
}
