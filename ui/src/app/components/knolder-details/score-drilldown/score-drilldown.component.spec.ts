import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import {ScoreDrilldownComponent} from './score-drilldown.component';

describe('ScoreDrilldownComponent', () => {
    let component: ScoreDrilldownComponent;
    let fixture: ComponentFixture<ScoreDrilldownComponent>;

    beforeEach(waitForAsync(() => {
        TestBed.configureTestingModule({
            declarations: [ScoreDrilldownComponent],
        }).compileComponents();

        fixture = TestBed.createComponent(ScoreDrilldownComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    }));

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
