import {Component, OnInit} from '@angular/core';
import {CardDataModel} from '../../models/cardData.model';
import {AuthorModel} from '../../models/author.model';
import {EmployeeActivityService} from '../../services/employee-activity.service';
import {TableHeaderModel} from '../../models/tableHeader.model';

@Component({
    selector: 'app-main',
    templateUrl: './main.page.html',
    styleUrls: ['./main.page.scss'],
})
export class MainPage implements OnInit {
    cardData: CardDataModel[];
    employeeData: AuthorModel[];
    dataKeys: string[];
    tableHeaders: TableHeaderModel[] = [
        {title: 'Author Name', id: 'name'},
        {title: 'Score', id: 'score'},
        {title: 'Rank', id: 'rank' },
        {title: 'Monthly Score', id: 'm-score' },
        {title: 'Monthly Rank', id: 'm-rank'},
        {title: '3 Monthly Streak', id: 'streak'}
    ];

    constructor(private service: EmployeeActivityService) {
    }

    ngOnInit() {
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
        const bgColorPurple = '#A38CC6';
        const bgColorYellow = '#FFCA5B';
        const bgColorGreen = '#4DE5D5';
        const bgColorBlue = '#6AB8F7';
        const fontColor = 'white';
        this.cardData = [
            {
                cardTitle: this.getTotalCount('blogs'), subtitle: 'Blogs', titleFontSize: fontSizeTitle,
                subtitleFontSize: fontSizeSubTitle, bgColor: bgColorPurple, fontColor
            }, {
                cardTitle: this.getTotalCount('knolx'), subtitle: 'Knolx', titleFontSize: fontSizeTitle,
                subtitleFontSize: fontSizeSubTitle, bgColor: bgColorYellow, fontColor
            }, {
                cardTitle: this.getTotalCount('webinars'), subtitle: 'Webinars', titleFontSize: fontSizeTitle,
                subtitleFontSize: fontSizeSubTitle, bgColor: bgColorGreen, fontColor
            }, {
                cardTitle: this.getTotalCount('techhubTemplates'), subtitle: 'TechHub Templates',
                titleFontSize: fontSizeTitle, subtitleFontSize: fontSizeSubTitle, bgColor: bgColorBlue,
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
