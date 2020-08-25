import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';
import { HeadersComponent } from './headers.component';
import { Component } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';
import { By } from '@angular/platform-browser';
import { AngularFireModule } from '@angular/fire';
import { environment } from '../../../environments/environment';
import { AngularFirestoreModule } from '@angular/fire/firestore';
import { AngularFireAuthModule } from '@angular/fire/auth';

describe('HeadersComponent', () => {
  let component: HeadersComponent;
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
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have title TEST', () => {
    expect(component.title).toEqual('LEADERBOARD');
  });

  it('should call onDropdown method', () => {
    spyOn(component, 'onDropdown');
    const element = fixture.debugElement.query(By.css('.dropdown-menu-btn'));
    element.triggerEventHandler('click', {});
    expect(component.onDropdown).toHaveBeenCalled();
  });

  it('should change the visibility status for logout button', () => {
    component.logoutBtnVisibility = false;
    component.onDropdown();
    expect(component.logoutBtnVisibility).toEqual(true);
  });

  it('should call onLogout method', () => {
    component.logoutBtnVisibility = true;
    fixture.detectChanges();
    spyOn(component, 'onLogout');
    const element = fixture.debugElement.queryAll(By.css('.dropdown-menu-item'))[1];
    element.triggerEventHandler('click', {});
    expect(component.onLogout).toHaveBeenCalled();
  });

  it('should call openForm method', () => {
    component.logoutBtnVisibility = true;
    fixture.detectChanges();
    spyOn(component, 'openForm');
    const element = fixture.debugElement.queryAll(By.css('.dropdown-menu-item'))[0];
    element.triggerEventHandler('click', {});
    expect(component.openForm).toHaveBeenCalled();
  });
});

@Component({
  selector: 'parent',
  template: '<app-headers></app-headers>'
})
class ParentComponent {
}
