import {NgModule} from '@angular/core';
import {PreloadAllModules, RouterModule, Routes} from '@angular/router';
import {AdminGuard} from './services/route-guards/admin/admin.guard';
import {AuthGuard} from './services/route-guards/auth/auth.guard';


const routes: Routes = [
    {
        path: '',
        canActivate: [AuthGuard],
        loadChildren: () => import('./pages/user/user.module').then(m => m.UserPageModule),
    },
    {
        path: 'login',
        loadChildren: () => import('./pages/login/login.module').then(m => m.LoginPageModule),
    },
    {
        path: 'admin',
        loadChildren: () => import('./pages/admin/admin.module').then(m => m.AdminPageModule),
        canActivate: [AdminGuard],
    },
];

@NgModule({
    imports: [RouterModule.forRoot(routes, {preloadingStrategy: PreloadAllModules})],
    exports: [RouterModule],
})
export class AppRoutingModule {
}
