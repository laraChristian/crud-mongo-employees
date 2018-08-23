import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { ServiceUtils, Apis, Resources } from './serviceUtils';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { DepartmentResponse } from '../domain/DepartmentResponse';
import { map, catchError } from 'rxjs/operators';
import { DepartmentRequest } from '../domain/departmentRequest';

@Injectable()
export class AdministrativesService extends ServiceUtils {

  constructor(private _httpRequest: Http) {
    super(_httpRequest)
  }

  public listDepartments(): Observable<DepartmentResponse> {
    console.log('start -- list-departments method')
    return this.get(Apis.ADMINISTRATIVE_API.toString(), Resources.LIST_DEPARTMENTS.toString());
  }

  public createDepartment(request: DepartmentRequest): Observable<DepartmentResponse> {
    console.log('start -- create-department method');
    return this.post(request, Apis.ADMINISTRATIVE_API.toString(), Resources.CREATE_DEPARTMENT.toString());
  }

  public deleteDepartment(request: DepartmentRequest): Observable<DepartmentResponse>{
    console.log('start -- delete-department method');
    return this.post(request, Apis.ADMINISTRATIVE_API, Resources.DELETE_DEPARTMENT); 
  }
}
