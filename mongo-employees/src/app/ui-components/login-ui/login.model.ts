import { User } from "../../entities/user";
import { LoginRequest } from "../../domain/loginRequest";

export class LoginModel {

    loggedIn: boolean;
    request: LoginRequest;
    user: User;
    passMessage: boolean;

    constructor() {
        this.clean();
        this.passMessage = false;
    }


    clean(): void {
        this.user = new User();
        this.request = new LoginRequest();
    }
}