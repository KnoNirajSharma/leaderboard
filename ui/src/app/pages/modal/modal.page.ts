import {Component, Input, OnInit} from '@angular/core';
import {ModalController, NavParams} from '@ionic/angular';
import {TableHeaderModel} from '../../models/tableHeader.model';
import {AuthorModel} from '../../models/author.model';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-modal',
  templateUrl: './modal.page.html',
  styleUrls: ['./modal.page.scss'],
})
export class ModalPage implements OnInit {

  @Input() authorData: AuthorModel;
  @Input() authorHeader: TableHeaderModel[];

  constructor(private modalController: ModalController) {
  }
  ngOnInit() {
  }

  async closeModal() {
    const onClosedData = '';
    await this.modalController.dismiss(onClosedData);
  }
}
