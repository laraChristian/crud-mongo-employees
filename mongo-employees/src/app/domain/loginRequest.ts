import { User } from "../entities/user";

export class LoginRequest {

    user: User;

    constructor() {
        this.user = new User();
    }
}