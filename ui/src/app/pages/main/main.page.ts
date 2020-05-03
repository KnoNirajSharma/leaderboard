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
    employeeData: AuthorModel[];
    private cardDataForMainPg: CardDataModel[];
    private blogCount = 0;
    private knolxCount = 0;
    private webinarCount = 0;
    private techhubTemplateCount = 0;
    private cardTitleFontSize = '3em';
    private cardSubtitleFontSize = '1.5em';
    private cardBgColor = '#0162D2';
    private cardFontColor = 'white';

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
        this.cardDataForMainPg = [
            {
                cardTitle: this.getTotalCount('blogs').toString(), subtitle: 'Blogs', titleFontSize: this.cardTitleFontSize,
                subtitleFontSize: this.cardSubtitleFontSize, bgColor: this.cardBgColor, fontColor: this.cardFontColor
            },

            {
                cardTitle: this.getTotalCount('knolx').toString(), subtitle: 'Knolx', titleFontSize: this.cardTitleFontSize,
                subtitleFontSize: this.cardSubtitleFontSize, bgColor: this.cardBgColor, fontColor: this.cardFontColor
            },

            {
                cardTitle: this.getTotalCount('webinars').toString(), subtitle: 'Webinars', titleFontSize: this.cardTitleFontSize,
                subtitleFontSize: this.cardSubtitleFontSize, bgColor: this.cardBgColor, fontColor: this.cardFontColor
            },

            {
                cardTitle: this.getTotalCount('techhubTemplates').toString(), subtitle: 'TechHub Templates',
                titleFontSize: this.cardTitleFontSize, subtitleFontSize: this.cardSubtitleFontSize, bgColor: this.cardBgColor,
                fontColor: this.cardFontColor
            },
        ];
    }

    getTotalCount(category: string): number {
        if (category === 'blogs') {
            this.blogCount = (this.employeeData.reduce((sum, current) => sum + current.score / 5, 0));
            return this.blogCount;
        }
        if (category === 'knolx') {
            return this.knolxCount;
        }
        if (category === 'webinars') {
            return this.webinarCount;
        }
        if (category === 'techhubTemplates') {
            return this.techhubTemplateCount;
        }
    }
}
