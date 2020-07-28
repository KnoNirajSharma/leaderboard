import { NgModule} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouteReuseStrategy } from '@angular/router';
import { IonicModule, IonicRouteStrategy } from '@ionic/angular';
import { SplashScreen } from '@ionic-native/splash-screen/ngx';
import { StatusBar } from '@ionic-native/status-bar/ngx';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { HttpIntercept } from './interceptors/http.intercept';
import { MainPageModule } from './pages/main/main.module';
import { DetailsPageModule } from './pages/details/details.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AuthServiceConfig, GoogleLoginProvider, SocialLoginModule } from 'angular-6-social-login';
import { LoginPageModule } from './pages/login/login.module';
import { environment } from '../environments/environment';

@NgModule({
    declarations: [AppComponent],
    entryComponents: [],
    imports: [BrowserModule,
        IonicModule.forRoot(),
        AppRoutingModule,
        HttpClientModule,
        MainPageModule,
        DetailsPageModule,
        BrowserAnimationsModule,
        SocialLoginModule,
        LoginPageModule],
    providers: [
        StatusBar,
        SplashScreen,
        {provide: HTTP_INTERCEPTORS, useClass: HttpIntercept, multi: true},
        {provide: RouteReuseStrategy, useClass: IonicRouteStrategy},
        ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
