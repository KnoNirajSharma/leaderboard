import { Component } from '@angular/core';

@Component({
  selector: 'app-report-issue',
  templateUrl: './report-issue.page.html',
  styleUrls: ['./report-issue.page.scss'],
})
export class ReportIssuePage {
  kekaUrl = 'https://knoldus.keka.com';

  openKeka() {
    window.open(this.kekaUrl, '_blank');
  }
}
