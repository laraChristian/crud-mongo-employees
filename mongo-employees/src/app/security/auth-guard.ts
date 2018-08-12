import { Injectable } from "../../../node_modules/@angular/core";
import { WebModel, SessionService } from "../services/session.service";
import { Router, CanActivate } from "@angular/router";
import { LoginResponse } from "../domain/loginResponse";

@Injectable()
export class AuthGuard implements CanActivate {

    loginModel: LoginResponse;

    constructor(private _router: Router, private _session: SessionService) { }

    canActivate() {
        this.loginModel = this._session.restoreStatus(WebModel.LOGIN, new LoginResponse());

        if (this.loginModel.success) {
            console.log('user logged successfully');
            // logged in so return true
            return true;
        }
        console.log('user not logged');
        // not logged in so redirect to login page
        this._router.navigate(['/loggin']);
        return false;
    }
}