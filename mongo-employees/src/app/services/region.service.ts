import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { ServiceUtils, Resources, Apis } from './serviceUtils';
import { RegionRequest } from '../domain/regionRequest';
import { Http } from '@angular/http';
import { map, catchError } from 'rxjs/operators'
import { Observable } from 'rxjs';
import { RegionResponse } from '../domain/regionResponse';

const API_BASE = environment.apiUrl;

@Injectable()
export class RegionService extends ServiceUtils {

  constructor(private _http: Http) {
    super();
  }

  public createRegions(request: RegionRequest): Observable<RegionResponse> {
    console.log(request);
    return this._http.post(API_BASE + Apis.REGIONS_API + Resources.CREATE_REGION, request, this.options).pipe(map(resp => {
      console.log('Response ' + resp.status)
      if (resp.status == 200 || resp.status == 202) {
        return resp.json();
      } else {
        throw new Error(resp.statusText);
      }
    }, catchError((error: Error) => {
      return Observable.throw(error)
    })));
  }

  public listRegions(): Observable<RegionResponse> {
    return this._http.get(API_BASE + Apis.REGIONS_API + Resources.LIST_REGIONS, this.options).pipe(map(resp => {
      if (resp.ok == true) {
        return resp.json();
      } else {
        throw new Error(resp.statusText);
      }
    }, catchError((error: any) => Observable.throw(error))));
  }


  public deleteRegion(request: RegionRequest): Observable<RegionResponse> {
    return this._http.post(API_BASE + Apis.REGIONS_API + Resources.DELETE_REGION, request, this.options).pipe(map(resp => {
      if (resp.ok == true) {
        return resp.json();
      } else {
        throw new Error(resp.statusText);
      }
    }, catchError((error: any) => Observable.throw(error))));
  }
}
