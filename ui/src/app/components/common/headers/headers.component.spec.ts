import {async, ComponentFixture, inject, TestBed} from '@angular/core/testing';
import {AngularFireModule} from '@angular/fire';
import {AngularFireAuthModule} from '@angular/fire/auth';
import {AngularFirestoreModule} from '@angular/fire/firestore';
import {Router} from '@angular/router';
import {RouterTestingModule} from '@angular/router/testing';
import {IonicModule} from '@ionic/angular';

import {environment} from '../../../../environments/environment';
import {LoginService} from '../../../services/login/login.service';
import {DropdownMenuListComponent} from '../dropdown-menu-list/dropdown-menu-list.component';
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
                DropdownMenuListComponent,
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

    it('should have admin as option if user role is admin true', () => {
        spyOn(loginService, 'isAdmin').and.returnValue(true);
        component.ngOnInit();
        expect(component.dropdownMenuList[1].value).toEqual('Admin');
    });

    it('should have not admin as option if user role is admin false', () => {
        spyOn(loginService, 'isAdmin').and.returnValue(false);
        component.ngOnInit();
        expect(component.dropdownMenuList[1].value).not.toEqual('Admin');
        expect(component.dropdownMenuList.length).toEqual(2);
    });

    it('should call openForm method if add contribution item is clicked', () => {
        spyOn(component, 'openForm');
        component.onDropdownClick('Add Contribution');
        expect(component.openForm).toHaveBeenCalled();
    });

    it('should call onLogout method if logout item is clicked', () => {
        spyOn(component, 'onLogout');
        component.onDropdownClick('Logout');
        expect(component.onLogout).toHaveBeenCalled();
    });

    it('should call navigate to /admin admin item is clicked', inject([Router], (router) => {
        spyOn(router, 'navigate').and.stub();
        component.onDropdownClick('Admin');
        expect(router.navigate).toHaveBeenCalledWith(['/', 'admin']);
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
