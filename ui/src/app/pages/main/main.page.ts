import {Component, OnInit} from '@angular/core';
import {CardDataModel} from '../../models/cardData.model';
import {AuthorModel} from '../../models/author.model';
import {EmployeeActivityService} from '../../services/employee-activity.service';
import {PageTitleModel} from '../../models/pageTitle.model';

@Component({
    selector: 'app-main',
    templateUrl: './main.page.html',
    styleUrls: ['./main.page.scss'],
})
export class MainPage implements OnInit {
    cardData: CardDataModel[];
    employeeData: AuthorModel[];
    titleKeys: string[];
    dataKeys: string[];
    pageTitles: PageTitleModel[];

    constructor(private service: EmployeeActivityService) {
    }

    ngOnInit() {
        this.pageTitles = [
            {title: 'Author Name'},
            {title: 'Score'},
            {title: 'Rank'},
            {title: 'Monthly Score'},
            {title: 'Monthly Rank'},
            {title: '3 Monthly Streak'}
        ];
        this.titleKeys = Object.keys(this.pageTitles[0]);
        this.service.getData()
            .subscribe((data: AuthorModel[]) => {
                this.employeeData = data.map((value: AuthorModel) => {
                    value.monthlyScore = 'N/A';
                    value.monthlyRank = 'N/A';
                    value.monthlyStreak = 'N/A';
                    return value;
                });
                this.dataKeys = Object.keys(this.employeeData[0]);
                this.prepareCardData();
            });
    }

    prepareCardData() {
        const fontSizeTitle = '3em';
        const fontSizeSubTitle = '1.5em';
        const backgroundColor = '#0162D2';
        const fontColor = 'white';
        this.cardData = [
            {
                cardTitle: this.getTotalCount('blogs'), subtitle: 'Blogs', titleFontSize: fontSizeTitle,
                subtitleFontSize: fontSizeSubTitle, bgColor: backgroundColor, fontColor
            }, {
                cardTitle: this.getTotalCount('knolx'), subtitle: 'Knolx', titleFontSize: fontSizeTitle,
                subtitleFontSize: fontSizeSubTitle, bgColor: backgroundColor, fontColor
            }, {
                cardTitle: this.getTotalCount('webinars'), subtitle: 'Webinars', titleFontSize: fontSizeTitle,
                subtitleFontSize: fontSizeSubTitle, bgColor: backgroundColor, fontColor
            }, {
                cardTitle: this.getTotalCount('techhubTemplates'), subtitle: 'TechHub Templates',
                titleFontSize: fontSizeTitle, subtitleFontSize: fontSizeSubTitle, bgColor: backgroundColor,
                fontColor
            },
        ];
    }

    getTotalCount(category: string): string {
        let count = '0';
        switch (category) {
            case 'blogs': {
                count = (this.employeeData.reduce((sum, current) => sum + current.score / 5, 0)).toString();
                break;
            }
            case 'knolx': {
                break;
            }
            case 'webinars': {
                break;
            }
            case 'techhubTemplates': {
                break;
            }
        }
        return count;
    }
}
