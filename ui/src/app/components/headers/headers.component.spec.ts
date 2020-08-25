import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';
import { HeadersComponent } from './headers.component';
import { Component } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';
import { AngularFireModule } from '@angular/fire';
import { environment } from '../../../environments/environment';
import { AngularFirestoreModule } from '@angular/fire/firestore';
import { AngularFireAuthModule } from '@angular/fire/auth';
import {LoginService} from '../../services/login.service';

describe('HeadersComponent', () => {
  let component: HeadersComponent;
  let loginService: LoginService;
  let fixture: ComponentFixture<ParentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        HeadersComponent,
        ParentComponent
      ],
      imports: [
        IonicModule.forRoot(),
        RouterTestingModule,
        AngularFireModule.initializeApp(environment.firebaseConfig, 'angular-auth-firebase'),
        AngularFirestoreModule,
        AngularFireAuthModule
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(ParentComponent);
    component = fixture.debugElement.children[0].componentInstance;
    loginService = TestBed.get(LoginService);
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should change the visibility status for dropdown menu button', () => {
    component.dropdownMenuVisibility = false;
    component.onDropdown();
    expect(component.dropdownMenuVisibility).toEqual(true);
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
});

@Component({
  selector: 'parent',
  template: '<app-headers></app-headers>'
})
class ParentComponent {
}
