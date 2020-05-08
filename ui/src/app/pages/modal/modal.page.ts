import {Component, Input, OnInit} from '@angular/core';
import {AuthorModel} from '../../models/author.model';
import {ModalController, NavParams} from '@ionic/angular';
import {TableHeaderModel} from '../../models/tableHeader.model';

@Component({
  selector: 'app-modal',
  templateUrl: './modal.page.html',
  styleUrls: ['./modal.page.scss'],
})
export class ModalPage implements OnInit {
  authorDetails: AuthorModel;
  authorHeaders: TableHeaderModel;

  constructor(private modalController: ModalController, private navParams: NavParams) {
  }
  ngOnInit() {
    this.authorDetails = this.navParams.data.authorData;
    this.authorHeaders = this.navParams.data.tableHeader;
    console.log(this.authorDetails);
    console.log(this.authorHeaders);
  }

  async closeModal() {
    const onClosedData = '';
    await this.modalController.dismiss(onClosedData);
  }
}
