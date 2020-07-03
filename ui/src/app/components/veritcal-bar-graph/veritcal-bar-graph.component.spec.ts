import {async, ComponentFixture, TestBed} from '@angular/core/testing';
import {IonicModule} from '@ionic/angular';

import {VeritcalBarGraphComponent} from './veritcal-bar-graph.component';
import {Component} from '@angular/core';
import {NgxChartsModule} from '@swimlane/ngx-charts';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

describe('VeritcalBarGraphComponent', () => {
    let component: VeritcalBarGraphComponent;
    let fixture: ComponentFixture<ParentComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [
                VeritcalBarGraphComponent,
                ParentComponent
            ],
            imports: [
                IonicModule.forRoot(),
                NgxChartsModule,
                BrowserAnimationsModule
            ]
        }).compileComponents();

        fixture = TestBed.createComponent(ParentComponent);
        component = fixture.debugElement.children[0].componentInstance;
        fixture.detectChanges();
    }));

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});

@Component({
    selector: 'parent',
    template: '<app-veritcal-bar-graph [inputResult]="dummyTrendsData"></app-veritcal-bar-graph>'
})
class ParentComponent {
    dummyTrendsData = [
        {
            month: 'JUNE',
            year: 2020,
            score: 4
        },
        {
            month: 'JULY',
            year: 2020,
            score: 6
        }
    ];
}
