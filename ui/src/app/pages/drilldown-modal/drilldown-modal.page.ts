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
    modalData: KnolderDetailsModel = {
        blogDetails: [{}],
        blogScore: 0,
        monthlyScore: 0,
        allTimeScore: 0,
        knolderName: ''
    };
    modalContent;

    constructor(private service: EmployeeActivityService, private modalController: ModalController) {
    }

    ngOnInit() {
        this.service.getDetails()
            .subscribe((data: KnolderDetailsModel) => {
                this.modalData = data;
                this.modalData.blogDetails.map((value) => {
                    this.modalContent =  `<div>` + data.knolderName + `</div>
                    <div>` + data.allTimeScore + `</div>
                    <div>` + data.monthlyScore + `</div>
                    <div>` + data.blogScore + `</div>
                    <div>` + value.title + value.date + `</div>`;
                });
            });
    }

    async closeModal() {
        const onClosedData = '';
        await this.modalController.dismiss(onClosedData);
    }
}
