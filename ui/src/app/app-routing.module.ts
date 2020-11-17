import {NgModule} from '@angular/core';
import {PreloadAllModules, RouterModule, Routes} from '@angular/router';

import {AdminGuard} from './route-guards/admin/admin.guard';
import {AuthGuard} from './route-guards/auth/auth.guard';

const routes: Routes = [
    {
        path: '',
        loadChildren: './pages/user/user.module#UserPageModule',
        canActivate: [AuthGuard],
    },
    {
        path: 'login',
        loadChildren: './pages/login/login.module#LoginPageModule',
    },
    {
        path: 'admin',
        loadChildren: './pages/admin/admin.module#AdminPageModule',
        canActivate: [AdminGuard],
    },
];

@NgModule({
    imports: [RouterModule.forRoot(routes, {preloadingStrategy: PreloadAllModules})],
    exports: [RouterModule],
})
export class AppRoutingModule {
}
