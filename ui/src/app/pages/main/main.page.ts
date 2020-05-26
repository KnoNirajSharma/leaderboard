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
    tableHeaders: TableHeaderModel[];
    currentlySelectedTab = 'overall';
    tabs = [
        {tabName: 'All time', id: 'overall'},
        {tabName: 'Monthly', id: 'monthly'},
        {tabName: '3 month streak', id: 'streak'}
    ];
    pageTitle = 'LEADERBOARD';

    constructor(private service: EmployeeActivityService) {
    }

    ngOnInit() {
        this.service.getData()
            .subscribe((data: AuthorModel[]) => {
                this.employeeData = data;
                this.dataKeys = Object.keys(this.employeeData[0]);
                this.prepareCardData();
            });
        this.tableHeaders = [
            {title: 'Name'},
            {title: 'Score'},
            {title: 'Rank'},
        ];
    }

    selectTab(value) {
        if (this.currentlySelectedTab !== value) {
            this.currentlySelectedTab = value;
            const tabs = document.querySelectorAll<HTMLElement>('.tab');
            for (let index = 0; index < tabs.length; index++) {
                tabs.item(index).classList.remove('selected');
            }
            document.querySelector<HTMLElement>('#' + value).classList.add('selected');
            this.populateTable();
        } else {
            this.currentlySelectedTab = value;
        }
    }

    populateTable() {
        switch (this.currentlySelectedTab) {
            case 'overall': {
                this.tableHeaders = [
                    {title: 'Name'},
                    {title: 'Score'},
                    {title: 'Rank'},
                ];
                this.service.getData()
                    .subscribe((data: AuthorModel[]) => {
                        this.employeeData = data;
                        this.dataKeys = Object.keys(this.employeeData[0]);
                    });
                break;
            }
            case 'monthly': {
                this.tableHeaders = [
                    {title: 'Name'},
                    {title: 'Monthly Score'},
                    {title: 'Monthly Rank'},
                ];
                this.service.getMonthlyData()
                    .subscribe((data: AuthorModel[]) => {
                        this.employeeData = data;
                        this.dataKeys = Object.keys(this.employeeData[0]);
                    });
                break;
            }
            case 'streak': {
                this.tableHeaders = [
                    {title: 'Name'},
                    {title: '3 Month Streak'},
                    {title: 'Rank'},
                ];
                this.service.getStreakData()
                    .subscribe((data: AuthorModel[]) => {
                        this.employeeData = data;
                        this.dataKeys = Object.keys(this.employeeData[0]);
                    });
                break;
            }
        }
    }

    prepareCardData() {
        const fontSizeTitle = '3em';
        const fontSizeSubTitle = '1.5em';
        const bgColorPurple = '#995d81';
        const bgColorRed = '#f78154';
        const bgColorGreen = '#5fad56';
        const bgColorBlue = '#3581B8';
        const fontColor = 'white';
        this.cardData = [
            {
                cardTitle: this.getTotalCount('blogs'), subtitle: 'Blogs', titleFontSize: fontSizeTitle,
                subtitleFontSize: fontSizeSubTitle, bgColor: bgColorPurple, fontColor
            }, {
                cardTitle: this.getTotalCount('knolx'), subtitle: 'Knolx', titleFontSize: fontSizeTitle,
                subtitleFontSize: fontSizeSubTitle, bgColor: bgColorRed, fontColor
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
