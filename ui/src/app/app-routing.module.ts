import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './auth.guard';

const routes: Routes = [
  {
    path: '',
    redirectTo: '/',
    pathMatch: 'full'
  },
  {
    path: '',
    loadChildren: './pages/main/main.module#MainPageModule',
    canActivate: [AuthGuard]
  },
  {
    path: 'details',
    loadChildren: './pages/details/details.module#DetailsPageModule',
    canActivate: [AuthGuard]
  },
  {
    path: 'login',
    loadChildren: './pages/login/login.module#LoginPageModule'
  },
  {
    path: 'hall-of-fame',
    loadChildren: './pages/hall-of-fame/hall-of-fame.module#HallOfFamePageModule',
    canActivate: [AuthGuard]
  },
  {
    path: 'about',
    loadChildren: './pages/about/about.module#AboutPageModule',
    canActivate: [AuthGuard]
  },
  {
    path: 'report-issue',
    loadChildren: './pages/report-issue/report-issue.module#ReportIssuePageModule',
    canActivate: [AuthGuard]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
