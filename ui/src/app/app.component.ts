import { Component } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { SplashScreen } from '@ionic-native/splash-screen/ngx';
import { StatusBar } from '@ionic-native/status-bar/ngx';
import { Platform } from '@ionic/angular';

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.scss'],
})
export class AppComponent {
  constructor(
    private platform: Platform,
    private splashScreen: SplashScreen,
    private statusBar: StatusBar,
    private router: Router,
  ) {
    this.initializeApp();
    // this.router.events.subscribe(event => {
    //   if (event instanceof NavigationEnd) {
    //     (window as any).ga('set', 'page', event.urlAfterRedirects);
    //     (window as any).ga('send', 'pageview');
    //     (window as any).ga('Both.send', 'pageview');
    //   }
    // });
  }

  initializeApp() {
    this.platform.ready().then(() => {
      this.statusBar.styleDefault();
      this.splashScreen.hide();
    });
  }
}
