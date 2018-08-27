import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { ServiceUtils, Apis, Resources } from './serviceUtils';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { DepartmentResponse } from '../domain/DepartmentResponse';
import { map, catchError } from 'rxjs/operators';
import { DepartmentRequest } from '../domain/departmentRequest';
import { JobResponse } from '../domain/jobResponse';
import { JobRequest } from '../domain/jobRequest';

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

  public deleteDepartment(request: DepartmentRequest): Observable<DepartmentResponse> {
    console.log('start -- delete-department method');
    return this.post(request, Apis.ADMINISTRATIVE_API, Resources.DELETE_DEPARTMENT);
  }

  public listJobs(): Observable<JobResponse> {
    console.log('start -- list-jobs method');
    return this.get(Apis.ADMINISTRATIVE_API.toString(), Resources.LIST_JOBS.toString());
  }

  public createJob(request: JobRequest): Observable<JobResponse> {
    console.log('start -- cerate method');
    return this.post(request, Apis.ADMINISTRATIVE_API.toString(), Resources.CREATE_JOB.toString());
  }

  public deleteJob(request: JobRequest): Observable<JobResponse> {
    console.log('start delete-job method');
    return this.post(request, Apis.ADMINISTRATIVE_API.toString(), Resources.DELETE_JOB.toString());
  }
}
