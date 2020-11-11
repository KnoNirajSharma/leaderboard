import {async, ComponentFixture, TestBed} from '@angular/core/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {IonicModule} from '@ionic/angular';

import {ComponentsModule} from '../../../components/components.module';
import {ReportIssuePage} from './report-issue.page';

describe('ReportIssuePage', () => {
    let component: ReportIssuePage;
    let fixture: ComponentFixture<ReportIssuePage>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [ReportIssuePage],
            imports: [
                IonicModule.forRoot(),
                RouterTestingModule,
                ComponentsModule,
                RouterTestingModule,
            ],
        }).compileComponents();

        fixture = TestBed.createComponent(ReportIssuePage);
        component = fixture.componentInstance;
    }));

    it('should call window.open with form url', () => {
        spyOn(window, 'open');
        component.openKeka();
        expect(window.open).toHaveBeenCalledWith(component.kekaUrl, '_blank');
    });
});
