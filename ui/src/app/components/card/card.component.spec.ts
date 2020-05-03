import {async, ComponentFixture, TestBed} from '@angular/core/testing';
import {IonicModule} from '@ionic/angular';

import {CardComponent} from './card.component';
import {Component} from '@angular/core';

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
    cardBgColor = 'white';
    cardFontColor = 'white';
    cardData = {
        cardTitle: 'test', subtitle: 'Blogs', titleFontSize: this.cardTitleFontSize,
        subtitleFontSize: this.cardSubtitleFontSize, bgColor: this.cardBgColor, fontColor: this.cardFontColor
    };
}
