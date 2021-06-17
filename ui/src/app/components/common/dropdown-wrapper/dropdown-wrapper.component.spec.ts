import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import {IonicModule} from '@ionic/angular';

import {DropdownWrapperComponent} from './dropdown-wrapper.component';

describe('DropdownWrapperComponent', () => {
    let component: DropdownWrapperComponent;
    let fixture: ComponentFixture<DropdownWrapperComponent>;

    beforeEach(waitForAsync(() => {
        TestBed.configureTestingModule({
            declarations: [DropdownWrapperComponent],
            imports: [IonicModule.forRoot()],
        }).compileComponents();

        fixture = TestBed.createComponent(DropdownWrapperComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    }));

    it('should make dropdownMenuVisibility to false if action is close', () => {
        component.dropdownMenuVisibility = true;
        component.controlDropdownMenu('close');
        expect(component.dropdownMenuVisibility).toBeFalsy();
    });

    it('should toggle dropdownMenuVisibility if action is toggle', () => {
        component.dropdownMenuVisibility = false;
        component.controlDropdownMenu('toggle');
        expect(component.dropdownMenuVisibility).toBeTruthy();
    });

    it('should call controlDropdownMenu this close parameter', () => {
        spyOn(component, 'controlDropdownMenu');
        spyOn(component.dropdownIconRef.nativeElement, 'contains').and.returnValue(false);
        component.onClick('<div>outside div</div>');
        expect(component.controlDropdownMenu).toHaveBeenCalledWith('close');
    });

    it('should call controlDropdownMenu this toggle parameter', () => {
        spyOn(component, 'controlDropdownMenu');
        spyOn(component.dropdownIconRef.nativeElement, 'contains').and.returnValue(true);
        component.onClick('<div>inside elem</div>');
        expect(component.controlDropdownMenu).toHaveBeenCalledWith('toggle');
    });
});
