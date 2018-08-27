import { Injectable } from "../../../node_modules/@angular/core";
import { Router, CanActivate } from "@angular/router";
import { SessionService, WebModel } from "../services/session.service";
import { LoginModel } from "../ui-components/login-ui/login.model";
import { LoginResponse } from "../domain/loginResponse";


@Injectable()
export class LogginGuard implements CanActivate {

    loginModel: LoginResponse;

    constructor(private _router: Router, private _session: SessionService) {
    }

    canActivate() {
        this.loginModel = this._session.restoreStatus(WebModel.LOGIN, new LoginResponse());

        if (this.loginModel.success) {
            console.log('user logged successfully');

            this._router.navigate(['/employees-ui']);
            return true;
        }
        console.log('user not logged');
        return true;
    }
}