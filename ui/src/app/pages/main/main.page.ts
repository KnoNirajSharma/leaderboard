import {Component, OnInit} from '@angular/core';
import {CardDataModel} from '../../models/cardData.model';
import {AuthorModel} from '../../models/author.model';
import {EmployeeActivityService} from '../../services/employee-activity.service';

@Component({
    selector: 'app-main',
    templateUrl: './main.page.html',
    styleUrls: ['./main.page.scss'],
})
export class MainPage implements OnInit {
    blogCount: any;
    knolxCount = 'N/A';
    webinarCount = 'N/A';
    techhubTemplateCount = 'N/A';
    employeeData: AuthorModel[];
    cardData: CardDataModel[];
    workCountList = [];
    cardTitleFontSize = '3em';
    cardSubtitleFontSize = '1.5em';
    cardBgColor = 'linear-gradient(to bottom, #157AE3, #096EDB, #0162D2, #0155C9, #0549BF)';
    cardFontColor = 'white';

    constructor(private service: EmployeeActivityService) {
    }

    ngOnInit() {
        this.service.getData()
            .subscribe((data: AuthorModel[]) => {
                this.employeeData = data;
                this.prepareCardData();
            });
    }

    prepareCardData() {
        this.workCountList = this.calCount();
        this.cardData = [
            new CardDataModel(this.workCountList[0], 'Blogs', '',
                this.cardTitleFontSize, this.cardSubtitleFontSize, this.cardBgColor, this.cardFontColor),

            new CardDataModel(this.workCountList[1], 'knolx', '',
                this.cardTitleFontSize, this.cardSubtitleFontSize, this.cardBgColor, this.cardFontColor),

            new CardDataModel(this.workCountList[2], 'Webinars', '',
                this.cardTitleFontSize, this.cardSubtitleFontSize, this.cardBgColor, this.cardFontColor),

            new CardDataModel(this.workCountList[3], 'TechHub Templates', '',
                this.cardTitleFontSize, this.cardSubtitleFontSize, this.cardBgColor, this.cardFontColor)
        ];
    }

    calCount(): Array<any> {
        this.blogCount = this.employeeData.reduce((sum, current) => sum + current.score / 5, 0);
        return [this.blogCount, this.knolxCount, this.webinarCount, this.techhubTemplateCount];
    }
}
