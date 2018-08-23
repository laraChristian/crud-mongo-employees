import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { ServiceUtils, Resources, Apis } from './serviceUtils';
import { RegionRequest } from '../domain/regionRequest';
import { Http } from '@angular/http';
import { map, catchError } from 'rxjs/operators'
import { Observable } from 'rxjs';
import { RegionResponse } from '../domain/regionResponse';
import { CountryRequest } from '../domain/countryRequest';
import { CountryResponse } from '../domain/countryResponse';
import { LocationResponse } from '../domain/locationReponse';
import { LocationRequest } from '../domain/locationRequest';

const API_BASE = environment.apiUrl;

@Injectable()
export class RegionService extends ServiceUtils {

  constructor(private _httpRequest: Http) {
    super(_httpRequest);
  }

  public createRegions(request: RegionRequest): Observable<RegionResponse> {
    console.log(request);
    return this._httpRequest.post(API_BASE + Apis.REGIONS_API + Resources.CREATE_REGION, request, this.options).pipe(map(resp => {
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

  public createCountry(request: CountryRequest): Observable<CountryResponse> {
    return this._http.post(API_BASE + Apis.COUNTRIES_API + Resources.CREATE_COUNTRY, request, this.options).pipe(map(resp => {
      if (resp.status == 200 || resp.status == 202) {
        return resp.json();
      } else {
        throw new Error(resp.statusText);
      }
    }), catchError((error: any) => Observable.throw(error)));
  }

  public listCountries(): Observable<CountryResponse> {
    return this._http.get(API_BASE + Apis.COUNTRIES_API + Resources.LIST_COUNTRIES, this.options).pipe(map(resp => {
      if (resp.ok == true) {
        return resp.json();
      } else {
        throw new Error(resp.statusText);
      }
    }, catchError((error: any) => Observable.throw(error))))
  }

  public deleteCountry(request: CountryRequest): Observable<CountryResponse> {
    return this._http.post(API_BASE + Apis.COUNTRIES_API + Resources.DELETE_COUNTRY, request, this.options).pipe(map(resp => {
      if (resp.status == 200 || resp.status == 202) {
        return resp.json();
      } else {
        throw new Error(resp.statusText);
      }
    }), catchError((error: any) => Observable.throw(error)));
  }

  public listLocations(): Observable<LocationResponse> {
    return this._http.get(API_BASE + Apis.LOCATIONS_API + Resources.LIST_LOCATIONS, this.options).pipe(map(resp => {
      if (resp.ok == true) {
        return resp.json();
      } else {
        throw new Error(resp.statusText);
      }
    }, catchError((error: any) => Observable.throw(error))));
  }

  public createLocation(request: LocationRequest): Observable<CountryResponse> {
    return this._http.post(API_BASE + Apis.LOCATIONS_API + Resources.CREATE_LOCATION, request, this.options).pipe(map(resp => {
      if (resp.status == 200 || resp.status == 202) {
        return resp.json();
      } else {
        throw new Error(resp.statusText);
      }
    }), catchError((error: any) => Observable.throw(error)));
  }

  public deleteteLocation(request: LocationRequest): Observable<CountryResponse> {
    return this._http.post(API_BASE + Apis.LOCATIONS_API + Resources.DELETE_LOCATION, request, this.options).pipe(map(resp => {
      if (resp.status == 200 || resp.status == 202) {
        return resp.json();
      } else {
        throw new Error(resp.statusText);
      }
    }), catchError((error: any) => Observable.throw(error)));
  }
}
