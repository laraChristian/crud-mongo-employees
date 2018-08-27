import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions } from '@angular/http';
import { Apis, Resources, ServiceUtils } from './serviceUtils';
import { Observable } from 'rxjs';
import { LoginResponse } from '../domain/loginResponse';
import { environment } from '../../environments/environment';
import { LoginRequest } from '../domain/loginRequest';
import { map, catchError } from 'rxjs/operators';
import { EmployeeResponse } from '../domain/employeeResponse';
import { EmployeeRequest } from '../domain/employeeRequest';
import { throws } from 'assert';

const API_URL = environment.apiUrl;

@Injectable()
export class EmployeeService extends ServiceUtils {

  constructor(private _httpClient: Http) {
    super(_httpClient);
  }

  public login(request: LoginRequest): Observable<LoginResponse> {
    console.log('start -- login method');
    return this._http.post(API_URL + Apis.EMPLOYEES_API + Resources.LOGIN, request, this.options).pipe(map(resp => resp.json()));
  }

  public listCmb(): Observable<EmployeeResponse> {
    console.log('start -- list-cmb method');
    return this._http.get(API_URL + Apis.EMPLOYEES_API + Resources.LIST_EMPLOYEES_CMB, this.options).pipe(map(resp => resp.json()));
  }

  public listEmployees(): Observable<EmployeeResponse> {
    console.log('start -- list-employees method');
    return this._http.get(API_URL + Apis.EMPLOYEES_API + Resources.LIST_EMPLOYEES, this.options).pipe(map(resp => resp.json()));
  }

  public create(request: EmployeeRequest): Observable<EmployeeResponse> {
    try {
      console.log('start -- create method');
      return this.post(request, Apis.EMPLOYEES_API, Resources.CREATE_EMPLOYEE);
    } finally {
      console.log('end -- create method')
    }
  }

  public delete(request: EmployeeRequest): Observable<EmployeeResponse> {
    try {
      console.log('start -- delete method');
      return this.post(request, Apis.EMPLOYEES_API, Resources.DELETE_EMPLOYEE);
    } finally {
      console.log('end -- delete method')
    }
  }

}
