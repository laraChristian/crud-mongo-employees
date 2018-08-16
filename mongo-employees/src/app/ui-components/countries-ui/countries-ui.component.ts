import { Component, OnInit } from '@angular/core';
import { MessageService } from 'primeng/primeng';
import { CountriesModel } from './countries.model';
import { FormBuilder, FormControl } from '@angular/forms';
import { RegionService } from '../../services/region.service';

@Component({
  selector: 'countries-ui',
  templateUrl: './countries-ui.component.html',
  styleUrls: ['./countries-ui.component.css'],
  providers: [RegionService]
})
export class CountriesUiComponent implements OnInit {

  private _model: CountriesModel;

  constructor(private _formBuilder: FormBuilder, private _messageService: MessageService, private _regionService: RegionService) {
    this._model = new CountriesModel(this._formBuilder);
  }

  ngOnInit() {
    this.listRegions();
    this.listCountries();
  }

  private listRegions(): void {
    console.log('start -- list-regions method');
    this._regionService.listRegions().subscribe(
      resp => {
        if (resp.success == true) {
          this._model.request.country.regionId = ' ';
          this._model.fillSelectItem(resp.regions, 'name', 'id', this._model.regionsItems);
        } else {
          console.error('[ERROR] ' + resp.message);
        }
      },
      error => {
        this._messageService.add({ severity: 'error', summary: 'Atention', detail: 'You should communicate with support immediately', life: 6000 });
        console.error('[ERROR] ' + error);
      },
      () => console.log('end -- list-region method')
    );
  }


  //#region CRUD
  private onSubmit(formData: any) {
    this._model.mapFormControlsToRequest(this._model.request.country, this._model.countryForm, ['id', 'name', 'regionId']);
    console.log(this._model.request);
    this.create();
  }

  private create(): void {
    this._regionService.createCountry(this._model.request).subscribe(
      resp => {
        if (resp.success == true) {
          this._messageService.add({ severity: 'success', summary: 'Atention', detail: resp.message.toString(), life: 6000 });
        } else {
          this._messageService.add({ severity: 'warn', summary: 'Atention', detail: resp.message.toString(), life: 6000 });
        }
      },
      error => {
        this._messageService.add({ severity: 'error', summary: 'Atention', detail: 'You should communicate with support immediately', life: 6000 });
        console.log('[ERROR] ' + error);
      }
    );
    this._model.clean();

    setTimeout(() => {
      this.listCountries();
    }, 3000);
  }

  private delete(): void {
    this._regionService.deleteCountry(this._model.request).subscribe(
      resp => {
        if (resp.success == true) {
          this._messageService.add({ severity: 'success', summary: 'Atention', detail: resp.message.toString(), life: 6000 });
        } else {
          this._messageService.add({ severity: 'warn', summary: 'Atention', detail: resp.message.toString(), life: 6000 });
        }
      },
      error => {
        this._messageService.add({ severity: 'error', summary: 'Atention', detail: 'You should communicate with support immediately', life: 6000 });
        console.log('[ERROR] ' + error);
      }
    );
    this._model.clean();
    setTimeout(() => {
      this.listCountries();
    }, 3000);
  }


  //#endregion

  //#region DATA-TABLE

  private listCountries(): void {
    console.log('start - list-countries method')
    this._regionService.listCountries().subscribe(
      resp => {
        if (resp.success == true) {
          this._model.countries = resp.countries;
          console.log(this._model.countries)
        }
      },
      error => {
        this._messageService.add({ severity: 'error', summary: 'Atention', detail: 'You should communicate with support immediately', life: 6000 });
        console.log('[ERROR] ' + error);
      }, () => console.log('end - list-countries method')
    );
  }

  onRowSelect(): void {
    this._model.request.update = true;
    this._model.enableDelete = false;
    this._model.mapRequestToFormControls(this._model.request.country, this._model.countryForm, ['id', 'name', 'regionId']);
    console.log(this._model.countryForm.value);
  }



  //#endregion
}
