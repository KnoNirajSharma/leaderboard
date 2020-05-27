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

  constructor(private modalController: ModalController) {
  }
  ngOnInit() {
  }

  async closeModal() {
    const onClosedData = '';
    await this.modalController.dismiss(onClosedData);
  }
}
