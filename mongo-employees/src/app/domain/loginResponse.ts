import { User } from "../entities/user";

export class LoginResponse {

    success: boolean;
    message: String;
    user: User;

    constructor() {
        this.success = false;
    }
}