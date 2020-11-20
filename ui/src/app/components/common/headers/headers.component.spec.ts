import {async, ComponentFixture, TestBed} from '@angular/core/testing';
import {AngularFireModule} from '@angular/fire';
import {AngularFireAuthModule} from '@angular/fire/auth';
import {AngularFirestoreModule} from '@angular/fire/firestore';
import {RouterTestingModule} from '@angular/router/testing';
import {IonicModule} from '@ionic/angular';

import {environment} from '../../../../environments/environment';
import {LoginService} from '../../../services/login/login.service';
import {DropdownWrapperComponent} from '../dropdown-wrapper/dropdown-wrapper.component';
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
                DropdownWrapperComponent,
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
});
