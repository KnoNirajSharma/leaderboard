import {async, ComponentFixture, fakeAsync, TestBed, tick} from '@angular/core/testing';
import {IonicModule, ModalController} from '@ionic/angular';
import {TableComponent} from './table.component';
import {RouterTestingModule} from '@angular/router/testing';
import {FormsModule} from '@angular/forms';
import {HttpClientTestingModule} from '@angular/common/http/testing';

describe('TableComponent', () => {
    let component: TableComponent;
    let fixture: ComponentFixture<TableComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [TableComponent],
            imports: [HttpClientTestingModule, IonicModule.forRoot(), RouterTestingModule, FormsModule],
            providers: [ModalController]
        }).compileComponents();

        fixture = TestBed.createComponent(TableComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    }));

    it('should create', () => {
        expect(component).toBeTruthy();
    });

    it ('should open when clicked `presentModal` button', fakeAsync( () => {
        spyOn(component, 'presentModal');
        const button = fixture.debugElement.nativeElement.querySelector('ion-row');
        fixture.detectChanges();
        button.click();
        tick();
        expect(component.presentModal).toBeTruthy('presentModal should now be true');
    }));
});
