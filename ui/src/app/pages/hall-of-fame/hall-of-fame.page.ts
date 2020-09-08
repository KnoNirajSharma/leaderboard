import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-hall-of-fame',
  templateUrl: './hall-of-fame.page.html',
  styleUrls: ['./hall-of-fame.page.scss'],
})
export class HallOfFamePage implements OnInit {
  list = [
    { month: 'August',
      year: 2020,
      leaders: [
        { knolderId: 1, knolderName: 'Umang Goyal', monthlyRank: 1, monthlyScore: 100, overallRank: 14, overallScore: 20 },
        { knolderId: 2, knolderName: 'Manish Kumar Mishra', monthlyRank: 2, monthlyScore: 100, overallRank: 24, overallScore: 2000 },
        { knolderId: 3, knolderName: 'Rudar Daman Singla', monthlyRank: 3, monthlyScore: 100, overallRank: 34, overallScore: 2 },
        { knolderId: 4, knolderName: 'Ayush Kumar Mishra', monthlyRank: 4, monthlyScore: 100, overallRank: 54, overallScore: 2000 },
        { knolderId: 5, knolderName: 'Gaurav Kumar Shukla', monthlyRank: 5, monthlyScore: 100, overallRank: 4, overallScore: 2000 },
      ] },
    { month: 'July',
      year: 2020,
      leaders: [
        { knolderId: 1, knolderName: 'Umang Goyal', monthlyRank: 1, monthlyScore: 100, overallRank: 4, overallScore: 200 },
        { knolderId: 2, knolderName: 'Abhinav Sharma', monthlyRank: 2, monthlyScore: 100, overallRank: 4, overallScore: 200 },
        { knolderId: 3, knolderName: 'Rudar Daman Singla', monthlyRank: 3, monthlyScore: 100, overallRank: 4, overallScore: 200 },
        { knolderId: 4, knolderName: 'Ayush Kumar Mishra', monthlyRank: 4, monthlyScore: 100, overallRank: 4, overallScore: 200 },
        { knolderId: 5, knolderName: 'Himanshu Gupta', monthlyRank: 5, monthlyScore: 100, overallRank: 4, overallScore: 200 },
      ] },
    { month: 'July',
      year: 2020,
      leaders: [
        { knolderId: 1, knolderName: 'Umang Goyal', monthlyRank: 1, monthlyScore: 100, overallRank: 4, overallScore: 200 },
        { knolderId: 2, knolderName: 'Abhinav Sharma', monthlyRank: 2, monthlyScore: 100, overallRank: 4, overallScore: 200 },
        { knolderId: 3, knolderName: 'Rudar Daman Singla', monthlyRank: 3, monthlyScore: 100, overallRank: 4, overallScore: 200 },
        { knolderId: 4, knolderName: 'Ayush Kumar Mishra', monthlyRank: 4, monthlyScore: 100, overallRank: 4, overallScore: 200 },
        { knolderId: 5, knolderName: 'Himanshu Gupta', monthlyRank: 5, monthlyScore: 100, overallRank: 4, overallScore: 200 },
      ] },
    { month: 'July',
      year: 2020,
      leaders: [
        { knolderId: 1, knolderName: 'Umang Goyal', monthlyRank: 1, monthlyScore: 100, overallRank: 4, overallScore: 200 },
        { knolderId: 2, knolderName: 'Abhinav Sharma', monthlyRank: 2, monthlyScore: 100, overallRank: 4, overallScore: 200 },
        { knolderId: 3, knolderName: 'Rudar Daman Singla', monthlyRank: 3, monthlyScore: 100, overallRank: 4, overallScore: 200 },
        { knolderId: 4, knolderName: 'Ayush Kumar Mishra', monthlyRank: 4, monthlyScore: 100, overallRank: 4, overallScore: 200 },
        { knolderId: 5, knolderName: 'Himanshu Gupta', monthlyRank: 5, monthlyScore: 100, overallRank: 4, overallScore: 200 },
      ] },
    { month: 'July',
      year: 2020,
      leaders: [
        { knolderId: 1, knolderName: 'Umang Goyal', monthlyRank: 1, monthlyScore: 100, overallRank: 4, overallScore: 200 },
        { knolderId: 2, knolderName: 'Abhinav Sharma', monthlyRank: 2, monthlyScore: 100, overallRank: 4, overallScore: 200 },
        { knolderId: 3, knolderName: 'Rudar Daman Singla', monthlyRank: 3, monthlyScore: 100, overallRank: 4, overallScore: 200 },
        { knolderId: 4, knolderName: 'Ayush Kumar Mishra', monthlyRank: 4, monthlyScore: 100, overallRank: 4, overallScore: 200 },
        { knolderId: 5, knolderName: 'Himanshu Gupta', monthlyRank: 5, monthlyScore: 100, overallRank: 4, overallScore: 200 },
      ] }
  ];

  constructor() {
  }

  ngOnInit() {
  }

}
