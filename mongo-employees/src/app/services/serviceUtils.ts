import { RequestOptions, Headers } from "@angular/http";

export class ServiceUtils {

    private _headers: Headers;
    private _options: RequestOptions;

    constructor() {
        this.initHeaders();
    }


    /**
     * Getter options
     * @return {RequestOptions}
     */
    protected get options(): RequestOptions {
        return this._options;
    }

    /**
     * Setter options
     * @param {RequestOptions} value
     */
    protected set options(value: RequestOptions) {
        this._options = value;
    }


    protected initHeaders(): void {
        this._headers = new Headers({ 'Content-Type': 'application/json' });
        this._options = new RequestOptions({ headers: this._headers });
    }

}

export enum Apis {
    EMPLOYEES_API = '/employees-api',
    REGIONS_API = '/regions-api'
}

export enum Resources {
    LOGIN = '/login',
    CREATE_REGION = '/create-update-region',
    LIST_REGIONS = '/list-regions',
    DELETE_REGION = '/delete-region'
}