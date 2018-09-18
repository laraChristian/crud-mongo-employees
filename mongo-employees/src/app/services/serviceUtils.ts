import { RequestOptions, Headers } from "@angular/http";
import { Http } from '@angular/http';
import { Observable } from "rxjs";
import { environment } from "../../environments/environment";
import { map, catchError } from "rxjs/operators";

const BASE_API = environment.apiUrl;

export class ServiceUtils {

    private _headers: Headers;
    private _options: RequestOptions;

    constructor(protected _http: Http) {
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

    protected post<T, O>(request: T, api: string, resource: string): Observable<O> {
        console.log('start -- post method');
        console.log(request);
        return this._http.post(BASE_API + api + resource, request, this.options).pipe(map(resp => {
            if (resp.ok === true) {
                return resp.json();
            } else {
                throw new Error(resp.statusText);
            }
        }, catchError((error: any) => Observable.throw(error))));
    }

    protected get<O>(api: string, resource: string): Observable<O> {
        console.log('start -- get method');
        return this._http.get(BASE_API + api + resource, this.options).pipe(map(resp => {
            console.info('get method status: ' + resp.status);
            if (resp.ok == true) {
                return resp.json()
            } else {
                throw new Error(resp.statusText);
            }
        }, catchError((error: any) => Observable.throw(error))));
    }

}

export enum Apis {
    EMPLOYEES_API = '/employees-api',
    REGIONS_API = '/regions-api',
    COUNTRIES_API = '/countries-api',
    LOCATIONS_API = '/locations-api',
    ADMINISTRATIVE_API = '/administrative-api'
}

export enum Resources {
    LOGIN = '/login',
    CREATE_REGION = '/create-update-region',
    LIST_REGIONS = '/list-regions',
    DELETE_REGION = '/delete-region',
    CREATE_COUNTRY = '/create-update-country',
    LIST_COUNTRIES = '/list-countries',
    DELETE_COUNTRY = '/delete-country',
    LIST_LOCATIONS = '/list',
    CREATE_LOCATION = '/create',
    DELETE_LOCATION = '/delete',
    CREATE_DEPARTMENT = '/create-department',
    LIST_DEPARTMENTS = '/list-departments',
    DELETE_DEPARTMENT = '/delete-department',
    LIST_JOBS = '/list-jobs',
    CREATE_JOB = '/create-job',
    DELETE_JOB = '/delete-job',
    LIST_EMPLOYEES_CMB = '/list-employees-cmb',
    CREATE_EMPLOYEE = '/create-employee',
    DELETE_EMPLOYEE = '/delete-emloyee',
    LIST_EMPLOYEES = '/list-employees'

}