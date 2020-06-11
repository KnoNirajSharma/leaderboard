import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Params} from '@angular/router';
import {EmployeeActivityService} from '../../services/employee-activity.service';
import {KnolderDetailsModel} from '../../models/knolder-details.model';

@Component({
  selector: 'app-details',
  templateUrl: './details.page.html',
  styleUrls: ['./details.page.scss'],
})
export class DetailsPage implements OnInit {
  knolderDetails: KnolderDetailsModel;
  knolderId: number;
  constructor(private route: ActivatedRoute,
              private service: EmployeeActivityService) { }

  ngOnInit() {
      this.route.params
          .subscribe(
              (params: Params) => {
                  this.knolderId = params.id;
              }
          );
      this.service.getDetails(this.knolderId)
        .subscribe((data: KnolderDetailsModel) => {
          this.knolderDetails = data;
        });
  }
}
