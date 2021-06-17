import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import {ScoreDetailsComponent} from './score-details.component';

describe('ScoreDetailsComponent', () => {
    let component: ScoreDetailsComponent;
    let fixture: ComponentFixture<ScoreDetailsComponent>;

    beforeEach(waitForAsync(() => {
        TestBed.configureTestingModule({
            declarations: [ScoreDetailsComponent],
        }).compileComponents();

        fixture = TestBed.createComponent(ScoreDetailsComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    }));

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
