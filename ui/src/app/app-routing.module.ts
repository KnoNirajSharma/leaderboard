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
    path: 'details/:id',
    loadChildren: './pages/details/details.module#DetailsPageModule',
    canActivate: [AuthGuard]
  },
  {
    path: 'login',
    loadChildren: './pages/login/login.module#LoginPageModule'
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
