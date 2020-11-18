import {Component} from '@angular/core';
import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ClickOutsideDirective} from './click-outside.directive';

@Component({
    template: `
        <div>
            <div appClickOutside (clickOutside)="toggleClick()">Oh I just love writing tests</div>
            <div> another div </div>
        </div>`,
})
class MockComponent {
    constructor() { }
    hasBeenClicked = false;
    toggleClick() {
        this.hasBeenClicked = !this.hasBeenClicked;
    }
}
describe('ClickOutsideDirective', () => {

    let component: MockComponent;
    let fixture: ComponentFixture<MockComponent>;
    let element: HTMLElement;
    let outSideDiv: HTMLElement;
    let insideDiv: HTMLElement;
    let anotherDiv: HTMLElement;

    beforeEach(() => {
        TestBed.configureTestingModule({
            declarations: [
                MockComponent,
                ClickOutsideDirective,
            ],
        });

        fixture = TestBed.createComponent(MockComponent);
        component = fixture.componentInstance;
        element = fixture.debugElement.nativeElement;
        outSideDiv = element.querySelectorAll('div')[0];
        insideDiv = element.querySelectorAll('div')[1];
        anotherDiv = element.querySelectorAll('div')[2];
        fixture.detectChanges();
    });

    it('click the outer div thus emitting the clickOutside and toggle the component value', () => {
        outSideDiv.click();
        fixture.detectChanges();
        expect(component.hasBeenClicked).toBeTruthy();
    });

    it('click the inner div thus not emitting the clickOutside', () => {
        insideDiv.click();
        fixture.detectChanges();
        expect(component.hasBeenClicked).not.toBeTruthy();
    });
});
