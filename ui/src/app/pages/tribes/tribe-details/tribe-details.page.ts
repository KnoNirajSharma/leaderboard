import { Component, OnInit } from '@angular/core';
import { TribeDetailsModel } from '../../../models/tribe-details.model';
import { EmployeeActivityService } from '../../../services/employee-activity.service';
import { ActivatedRoute, Params } from '@angular/router';
import { FormControl } from '@angular/forms';
import { AuthorModel } from '../../../models/author.model';
import { TableHeaderModel } from '../../../models/tableHeader.model';

@Component({
  selector: 'app-tribe-details',
  templateUrl: './tribe-details.page.html',
  styleUrls: ['./tribe-details.page.scss'],
})
export class TribeDetailsPage implements OnInit {
  tribeId: string;
  tribeDetails: TribeDetailsModel;
  searchBar = new FormControl('');
  tableHeading: TableHeaderModel[] = [
    { title: 'MONTHLY RANK' },
    { title: 'MONTHLY SCORE' },
    { title: 'OVERALL RANK' },
    { title: 'OVERALL SCORE' },
    { title: '3-MONTH-STREAK' }
  ];
  constructor(private employeeActivityService: EmployeeActivityService, private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.queryParams
      .subscribe((params: Params) => {
        this.tribeId = params.id;
        this.getTribeDetails(this.tribeId);
      });
  }

  getTribeDetails(id: string) {
    this.employeeActivityService.getTribeDetails(id)
      .subscribe((response: TribeDetailsModel) => {
        this.tribeDetails = { ...response, memberReputations: [...response.memberReputations] };
      });
  }
}
