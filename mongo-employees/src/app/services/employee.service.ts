import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions } from '@angular/http';
import { Apis, Resources } from './serviceUtils';
import { Observable } from 'rxjs';
import { LoginResponse } from '../domain/loginResponse';
import { environment } from '../../environments/environment';
import { LoginRequest } from '../domain/loginRequest';
import { map } from 'rxjs/operators';
import { EmployeeResponse } from '../domain/employeeResponse';

const API_URL = environment.apiUrl;

@Injectable()
export class EmployeeService {

  private _headers: Headers;
  private _options: RequestOptions;

  constructor(private _http: Http) {
    this._headers = new Headers({ 'Content-Type': 'application/json' });
    this._options = new RequestOptions({ headers: this._headers });
  }

  public login(request: LoginRequest): Observable<LoginResponse> {
    console.log('start -- login method');
    return this._http.post(API_URL + Apis.EMPLOYEES_API + Resources.LOGIN, request, this._options).pipe(map(resp => resp.json()));
  }

  public listCmb(): Observable<EmployeeResponse> {
    console.log('start -- list-cmb method');
    return this._http.get(API_URL + Apis.EMPLOYEES_API + Resources.LIST_EMPLOYEES_CMB, this._options).pipe(map(resp => resp.json()));
  }
}
