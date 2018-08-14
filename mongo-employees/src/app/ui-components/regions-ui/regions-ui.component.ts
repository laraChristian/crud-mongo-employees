import { Component, OnInit } from '@angular/core';
import { RegionModel } from './regions.model';
import { FormBuilder } from '../../../../node_modules/@angular/forms';
import { MessageService } from '../../../../node_modules/primeng/primeng';
import { RegionService } from '../../services/region.service';

@Component({
  selector: 'regions-ui',
  templateUrl: './regions-ui.component.html',
  styleUrls: ['./regions-ui.component.css'],
  providers: [RegionService]
})
export class RegionsUiComponent implements OnInit {

  private _model: RegionModel;

  constructor(private _formGroup: FormBuilder, private _messageService: MessageService, private _regionService: RegionService) {
    this._model = new RegionModel(_formGroup);
  }

  ngOnInit() {
    this.listAllRegions();
  }

  private onSubmit(formData: String): void {
    this._model.request.region = this._model.regionForm.value;
    console.log(this._model.request)
    this.createRegion();
  }

  private createRegion() {
    console.log('start -- create-region method');
    this._regionService.createRegions(this._model.request).subscribe(
      resp => {
        if (resp.success == true) {
          this._messageService.add({ severity: 'success', summary: 'Atention', detail: resp.message.toString(), life: 6000 });
          this.listAllRegions();
        } else {
          this._messageService.add({ severity: 'warn', summary: 'Atention', detail: resp.message.toString(), life: 6000 });
        }
      }, error => {
        this._messageService.add({ severity: 'error', summary: 'Atention', detail: 'You should communicate with support immediately', life: 6000 });
        console.error('[ERROR] ' + error);
      },
      () => console.log('end create-region method')
    );
    this._model.clean();
  }


  private deleteRegion() {
    console.log('start -- delete-region method');
    this._regionService.deleteRegion(this._model.request).subscribe(
      resp => {
        if (resp.success == true) {
          this._messageService.add({ severity: 'success', summary: 'Atention', detail: resp.message.toString(), life: 6000 });
          this.listAllRegions();
        } else {
          this._messageService.add({ severity: 'warn', summary: 'Atention', detail: resp.message.toString(), life: 6000 });
        }
      },
      error => {
        this._messageService.add({ severity: 'error', summary: 'Atention', detail: 'You should communicate with support immediately', life: 6000 });
        console.error('[ERROR] ' + error);
      },
      () => console.log('end -- delete-region method')
    );
    this._model.clean();
  }

  //#region data-table

  private listAllRegions(): void {
    this._model.loading = true;

    console.log('start - list-all method')
    this._regionService.listRegions().subscribe(
      resp => {
        if (resp.success == true) {
          this._model.regions = resp.regions;
        }
      },
      error => console.error('[ERROR] ' + error),
      () => console.log('end - list-all method')
    );
    this._model.loading = false;
  }

  onRowSelect() {
    this._model.enableDelete = false;
    this._model.request.update = true;
    this._model.regionForm.setValue({ 'name': this._model.request.region.name, 'id': this._model.request.region.id })
  }
  //#endregion

}
