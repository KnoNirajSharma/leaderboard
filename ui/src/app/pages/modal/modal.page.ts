import {Component, OnInit} from '@angular/core';
import {ModalController, NavParams} from '@ionic/angular';
import {TableHeaderModel} from '../../models/tableHeader.model';
import {AuthorModel} from '../../models/author.model';

@Component({
  selector: 'app-modal',
  templateUrl: './modal.page.html',
  styleUrls: ['./modal.page.scss'],
})
export class ModalPage implements OnInit {
  authorDetails: AuthorModel;
  authorHeaders: TableHeaderModel[];

  constructor(private modalController: ModalController, private navParams: NavParams) {
  }
  ngOnInit() {
    this.authorDetails = this.navParams.data.authorData;
    this.authorHeaders = this.navParams.data.tableHeader;
  }

  async closeModal() {
    const onClosedData = '';
    await this.modalController.dismiss(onClosedData);
  }
}
