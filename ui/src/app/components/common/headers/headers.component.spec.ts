import {async, ComponentFixture, TestBed} from '@angular/core/testing';
import {AngularFireModule} from '@angular/fire';
import {AngularFireAuthModule} from '@angular/fire/auth';
import {AngularFirestoreModule} from '@angular/fire/firestore';
import {RouterTestingModule} from '@angular/router/testing';
import {IonicModule} from '@ionic/angular';

import {environment} from '../../../../environments/environment';
import {LoginService} from '../../../services/login/login.service';
import {MenuBoxComponent} from '../menu-box/menu-box.component';
import {HeadersComponent} from './headers.component';

describe('HeadersComponent', () => {
    let component: HeadersComponent;
    let loginService: LoginService;
    let fixture: ComponentFixture<HeadersComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [
                HeadersComponent,
                MenuBoxComponent,
            ],
            imports: [
                IonicModule.forRoot(),
                RouterTestingModule,
                AngularFireModule.initializeApp(environment.firebaseConfig, 'angular-auth-firebase'),
                AngularFirestoreModule,
                AngularFireAuthModule,
            ],
        }).compileComponents();

        fixture = TestBed.createComponent(HeadersComponent);
        component = fixture.componentInstance;
        loginService = TestBed.get(LoginService);
    }));

    it('should toggle the visibility status for dropdown menu', () => {
        component.dropdownMenuVisibility = false;
        component.controlDropDownMenu('toggle');
        expect(component.dropdownMenuVisibility).toEqual(true);
    });

    it('should change the visibility status for dropdown menu to false', () => {
        component.dropdownMenuVisibility = true;
        component.controlDropDownMenu('close');
        expect(component.dropdownMenuVisibility).toEqual(false);
    });

    it('should call window.open with form url', () => {
        spyOn(window, 'open');
        component.openForm();
        expect(window.open).toHaveBeenCalledWith(component.formUrl, '_blank');
    });

    it('should call window.open with form url', () => {
        spyOn(loginService, 'logout');
        component.onLogout();
        expect(loginService.logout).toHaveBeenCalled();
    });

    it('should toggle the visibility status for menu box', () => {
        component.menuBoxVisibility = false;
        component.controlMenuBox('toggle');
        expect(component.menuBoxVisibility).toEqual(true);
    });

    it('should change the visibility status for dropdown menu to false', () => {
        component.dropdownMenuVisibility = true;
        component.controlMenuBox('close');
        expect(component.menuBoxVisibility).toEqual(false);
    });
});
