import { Component, OnInit } from '@angular/core';
import { LocationsModel } from './locations.model';
import { FormBuilder } from '@angular/forms';
import { RegionService } from '../../services/region.service';
import { MessageService, LazyLoadEvent } from 'primeng/primeng';
import { Locations } from '../../entities/locations';

@Component({
  selector: 'locations-ui',
  templateUrl: './locations-ui.component.html',
  styleUrls: ['./locations-ui.component.css'],
  providers: [RegionService]
})
export class LocationsUiComponent implements OnInit {

  private _model: LocationsModel;

  constructor(private _formBuilder: FormBuilder, private _regionService: RegionService, private _mesasgeService: MessageService) {
    this._model = new LocationsModel(this._formBuilder);
  }

  ngOnInit() {
    this.listCountries();
  }

  private listCountries(): void {
    this._regionService.listCountries().subscribe(
      resp => {
        if (resp.success == true) {
          this._model.request.location.countryId = ' ';
          this._model.fillSelectItem(resp.countries, 'name', 'id', this._model.countryItems, { label: 'Countries', value: '' });
        } else {
          console.error('[ERROR] ' + resp.success)
        }
      },
      error => {
        this._mesasgeService.add({ severity: 'error', summary: 'Atention', detail: 'You should communicate with support immediately', life: 6000 });
        console.error('[ERROR] ' + error);
      }
    )
  }


  //#region CRUD OPERATIONS

  private onSubmit(): void {
    this._model.mapFormControlsToRequest(this._model.request.location, this._model.locationForm, ['id', 'streetAddress', 'postalCode', 'city', 'stateProvince', 'countryId'])
    console.log(this._model.request)
    this.createLocation();
  }

  private createLocation(): void {
    this._regionService.createLocation(this._model.request).subscribe(
      resp => {
        if (resp.success == true) {
          this._mesasgeService.add({ severity: 'success', summary: 'Atention', detail: resp.message.toString(), life: 6000 });
          this.loadCarsLazy(null);
        } else {
          this._mesasgeService.add({ severity: 'warn', summary: 'Atention', detail: resp.message.toString(), life: 6000 });
        }
      },
      error => {
        this._mesasgeService.add({ severity: 'error', summary: 'Atention', detail: 'You should communicate immediately with support', life: 6000 });
      }
    );
    this._model.clean();
  }

  private onRowSelect(): void {
    this._model.request.update = true;
    this._model.disableDelete = false;
    this._model.mapRequestToFormControls(this._model.request.location, this._model.locationForm, ['id', 'streetAddress', 'postalCode', 'city', 'stateProvince', 'countryId']);
    console.log(this._model.request);
  }

  private deleteLocation(): void {
    this._regionService.deleteteLocation(this._model.request).subscribe(
      resp => {
        if (resp.success == true) {
          this._mesasgeService.add({ severity: 'success', summary: 'Atention', detail: resp.message.toString(), life: 6000 });
          this.loadCarsLazy(null);
        } else {
          this._mesasgeService.add({ severity: 'warn', summary: 'Atention', detail: resp.message.toString(), life: 6000 });
        }
      },
      error => {
        this._mesasgeService.add({ severity: 'error', summary: 'Atention', detail: 'You should communicate immediately with support', life: 6000 });
      }
    );
    this._model.clean();
  }
  //#endregion

  //#region DATA-TABLE
  loadCarsLazy(event: LazyLoadEvent) {
    this._model.loading = true;
    setTimeout(() => {
      this.listLocations();
    }, 3000);
  }


  private listLocations(): void {
    console.log('start -- list-regions method')
    this._regionService.listLocations().subscribe(
      resp => {
        if (resp.success == true) {
          this._model.locations = resp.locations;
          this._model.loading = false;
        }
      }, error => {
        console.log('[ERROR] ' + error)
      }, () => console.log('end -- list-regions method')
    )
  }
  //#endregion
}
