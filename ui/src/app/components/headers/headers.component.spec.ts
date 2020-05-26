import {async, ComponentFixture, TestBed} from '@angular/core/testing';
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

});

@Component({
    selector: 'parent',
    template: '<app-headers [title] = "title"></app-headers>'
})
class ParentComponent {
    title = 'test';
}
