import { UsersUiComponent } from "../ui-components/users-ui/users-ui.component";
import { AuthGuard } from "../security/auth-guard";
import { LogginGuard } from "../security/loggin-guard";
import { LoginUiComponent } from "../ui-components/login-ui/login-ui.component";
import { RouterModule, Routes } from "@angular/router";
import { RegionsUiComponent } from "../ui-components/regions-ui/regions-ui.component";
import { CountriesUiComponent } from "../ui-components/countries-ui/countries-ui.component";

const routes: Routes = [
    { path: '', redirectTo: 'loggin', pathMatch: 'full' },
    { path: 'users-ui', component: UsersUiComponent, canActivate: [AuthGuard] },
    { path: 'regions-ui', component: RegionsUiComponent, canActivate: [AuthGuard] },
    { path: 'countries-ui', component: CountriesUiComponent, canActivate: [AuthGuard] },
    { path: 'loggin', component: LoginUiComponent, canActivate: [LogginGuard] }
];

export const routing = RouterModule.forRoot(routes, { useHash: true });