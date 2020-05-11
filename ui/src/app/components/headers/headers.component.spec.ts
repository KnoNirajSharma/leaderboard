import {async, ComponentFixture, fakeAsync, TestBed, tick} from '@angular/core/testing';
import {IonicModule} from '@ionic/angular';
import {HeadersComponent} from './headers.component';
import {Component} from '@angular/core';
import {SidebarComponent} from '../sidebar/sidebar.component';
import {RouterTestingModule} from '@angular/router/testing';

describe('HeadersComponent', () => {
    let component: HeadersComponent;
    let fixture: ComponentFixture<ParentComponent>;
    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [HeadersComponent, ParentComponent, SidebarComponent],
            imports: [IonicModule.forRoot(), RouterTestingModule]
        }).compileComponents();

        fixture = TestBed.createComponent(ParentComponent);
        component = fixture.debugElement.children[0].componentInstance;
        fixture.detectChanges();
    }));

    it('should create', () => {
        expect(component).toBeTruthy();
    });

    it('should have title TEST', () => {
        expect(component.title).toEqual('test');
    });

    it('should call toggleSidebar function', fakeAsync(() => {
        spyOn(component, 'toggleSidebar');
        const button = fixture.debugElement.nativeElement.querySelector('ion-button');
        button.click();
        tick();
        expect(component.toggleSidebar).toHaveBeenCalled();
    }));

    it('should call toggleSidebar function', fakeAsync(() => {
        tick();
        const visibility = !component.visibility;
        component.toggleSidebar();
        expect(component.visibility).toEqual(visibility);
    }));
});

@Component({
    selector: 'parent',
    template: '<app-headers [title] = "title"></app-headers>'
})
class ParentComponent {
    title = 'test';
}
