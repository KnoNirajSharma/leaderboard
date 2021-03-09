import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {DropdownMenuListComponent} from './dropdown-menu-list.component';

describe('DropdownMenuListComponent', () => {
    let component: DropdownMenuListComponent;
    let fixture: ComponentFixture<DropdownMenuListComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [DropdownMenuListComponent],
        }).compileComponents();

        fixture = TestBed.createComponent(DropdownMenuListComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    }));

    it('should emit menuitemClicked event', () => {
        spyOn(component.menuItemClicked, 'emit');
        component.onItemClick('test');
        expect(component.menuItemClicked.emit).toHaveBeenCalledWith('test');
    });
});
