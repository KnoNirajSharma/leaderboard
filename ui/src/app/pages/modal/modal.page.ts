import {Component, Input, OnInit} from '@angular/core';
import {ModalController} from '@ionic/angular';
import {TableHeaderModel} from '../../models/tableHeader.model';
import {AuthorModel} from '../../models/author.model';

@Component({
  selector: 'app-modal',
  templateUrl: './modal.page.html',
  styleUrls: ['./modal.page.scss'],
})
export class ModalPage implements OnInit {

  @Input() modalContent;
  @Input() modalHeading;

  constructor(private modalController: ModalController) {
  }
  ngOnInit() {
    // this.modalContent = `<ion-row>
    //   <ion-col>` + this.authorData.knolderName + `</ion-col>
    //   <ion-col>` + this.authorData.score + `</ion-col>
    //   <ion-col>` + this.authorData.rank + `</ion-col>
    //   <ion-col>` + this.authorData.streakScore + `</ion-col>
    //   <ion-col>` + this.authorData.monthlyScore + `</ion-col>
    //   <ion-col>` + this.authorData.monthlyRank + `</ion-col>
    //   </ion-row>`;
    //
    // this.modalHeading = `<ion-row>
    //  <ion-col>` + this.authorHeader[0].title + `</ion-col>
    //  <ion-col>` + this.authorHeader[1].title + `</ion-col>
    //  <ion-col>` + this.authorHeader[2].title + `</ion-col>
    //  <ion-col>` + this.authorHeader[3].title + `</ion-col>
    //  <ion-col>` + this.authorHeader[4].title + `</ion-col>
    //  <ion-col>` + this.authorHeader[5].title + `</ion-col>
    //  </ion-row>`;
  }

  async closeModal() {
    const onClosedData = '';
    await this.modalController.dismiss(onClosedData);
  }
}
