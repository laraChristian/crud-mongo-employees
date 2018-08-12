import { Injectable } from "../../../node_modules/@angular/core";
import { LocalStorageService } from 'angular-2-local-storage';

@Injectable()
export class SessionService {

    constructor(private localStorageService: LocalStorageService) {
    }

    public restoreStatus<T>(key: WebModel, payload: T): T {
        let restoredPayload: T = this.localStorageService.get<T>(key.toString());

        if (restoredPayload === null) {
            this.localStorageService.set(key.toString(), payload);
        }

        return this.localStorageService.get<T>(key.toString());
    }

    //---to save status
    public save<T>(key: WebModel, payload: T): void {
        this.localStorageService.set(key.toString(), payload);
    }

    //--to retrieve data via key
    public retrieve<T>(key: WebModel): T {
        return this.localStorageService.get<T>(key.toString())
    }

    //--to clean memory browser
    public logout(): void {
        this.localStorageService.clearAll();
    }

}

export enum WebModel {
    LOGIN
}