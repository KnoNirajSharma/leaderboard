import {Component, ComponentFactoryResolver, OnInit, ViewChild, ViewContainerRef} from '@angular/core';
import {CardDataModel} from '../../models/cardData.model';
import {AuthorModel} from '../../models/author.model';
import {EmployeeActivityService} from '../../services/employee-activity.service';
import {TableComponent} from '../../components/table/table.component';
import {MonthlyTableComponent} from '../../components/monthly-table/monthly-table.component';
import {MonthlyStreakTableComponent} from '../../components/monthly-streak-table/monthly-streak-table.component';

@Component({
    selector: 'app-main',
    templateUrl: './main.page.html',
    styleUrls: ['./main.page.scss'],
})
export class MainPage implements OnInit {
    @ViewChild('dynamicTable', {static: true, read: ViewContainerRef}) entry: ViewContainerRef;
    componentRef: any;
    cardData: CardDataModel[];
    employeeData: AuthorModel[];
    tabValue = 'overall';
    tabData =  [
        {tabName: 'Overall', id: 'overall'},
        {tabName: 'Monthly', id: 'monthly'},
        {tabName: '3 month streak', id: 'streak'}
    ];

    constructor(private service: EmployeeActivityService,
                private resolver: ComponentFactoryResolver) {
    }

    ngOnInit() {
        this.service.getData()
            .subscribe((data: AuthorModel[]) => {
                this.employeeData = data;
                this.prepareCardData();
            });
        this.createComponent();
    }
    showTable(value) {
        this.tabValue = value;
        this.createComponent();
    }
    createComponent() {
        this.entry.clear();
        if (this.tabValue === 'overall') {
            const factory = this.resolver.resolveComponentFactory(TableComponent);
            this.componentRef = this.entry.createComponent(factory);
        }
        if (this.tabValue === 'monthly') {
            const factory = this.resolver.resolveComponentFactory(MonthlyTableComponent);
            this.componentRef = this.entry.createComponent(factory);
        }
        if (this.tabValue === 'streak') {
            const factory = this.resolver.resolveComponentFactory(MonthlyStreakTableComponent);
            this.componentRef = this.entry.createComponent(factory);
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
