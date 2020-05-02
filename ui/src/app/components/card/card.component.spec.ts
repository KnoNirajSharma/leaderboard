import {async, ComponentFixture, TestBed} from '@angular/core/testing';
import {IonicModule} from '@ionic/angular';

import {CardComponent} from './card.component';
import {Component} from '@angular/core';
import {CardDataModel} from '../../models/cardData.model';

describe('CardComponent', () => {
    let component: CardComponent;
    let fixture: ComponentFixture<ParentComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [CardComponent, ParentComponent],
            imports: [IonicModule.forRoot()]
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
    template: '<app-card [cardData]="cardData"></app-card>'
})
class ParentComponent {
    cardTitleFontSize = '3em';
    cardSubtitleFontSize = '1.5em';
    cardBgColor = 'linear-gradient(to bottom, #157AE3, #096EDB, #0162D2, #0155C9, #0549BF)';
    cardFontColor = 'white';
    cardData = new CardDataModel('test', 'test', 'test',
        this.cardTitleFontSize, this.cardSubtitleFontSize, this.cardBgColor, this.cardFontColor);
}
