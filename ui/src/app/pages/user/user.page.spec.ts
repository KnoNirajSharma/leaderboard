import {async, ComponentFixture, TestBed} from '@angular/core/testing';
import {AngularFireModule} from '@angular/fire';
import {AngularFireAuthModule} from '@angular/fire/auth';
import {AngularFirestoreModule} from '@angular/fire/firestore';
import {RouterTestingModule} from '@angular/router/testing';
import {IonicModule} from '@ionic/angular';

import {environment} from '../../../environments/environment';
import {ComponentsModule} from '../../components/components.module';
import {UserPage} from './user.page';

fdescribe('UserPage', () => {
    let component: UserPage;
    let fixture: ComponentFixture<UserPage>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [UserPage],
            imports: [
                IonicModule.forRoot(),
                RouterTestingModule,
                ComponentsModule,
                AngularFireModule.initializeApp(environment.firebaseConfig, 'angular-auth-firebase'),
                AngularFirestoreModule,
                AngularFireAuthModule,
            ],
        }).compileComponents();

        fixture = TestBed.createComponent(UserPage);
        component = fixture.componentInstance;
        fixture.detectChanges();
    }));

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
