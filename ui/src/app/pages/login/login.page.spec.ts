import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';
import { LoginPage } from './login.page';
import { AuthServiceConfig, GoogleLoginProvider, SocialLoginModule } from 'angular-6-social-login';
import { RouterTestingModule } from '@angular/router/testing';

describe('LoginPage', () => {
    let component: LoginPage;
    let fixture: ComponentFixture<LoginPage>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [LoginPage],
            imports: [IonicModule.forRoot(),
                SocialLoginModule,
                RouterTestingModule],
            providers: [{
                provide: AuthServiceConfig,
                useValue: new AuthServiceConfig([
                    {
                        id: GoogleLoginProvider.PROVIDER_ID,
                        provider: new GoogleLoginProvider('id')
                    }])
            }]
        }).compileComponents();

        fixture = TestBed.createComponent(LoginPage);
        component = fixture.componentInstance;
        fixture.detectChanges();
    }));

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
