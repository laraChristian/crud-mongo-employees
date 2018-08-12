import { Component, OnInit } from '@angular/core';
import { RegionModel } from './regions.model';
import { FormBuilder } from '../../../../node_modules/@angular/forms';
import { MessageService } from '../../../../node_modules/primeng/primeng';

@Component({
  selector: 'regions-ui',
  templateUrl: './regions-ui.component.html',
  styleUrls: ['./regions-ui.component.css']
})
export class RegionsUiComponent implements OnInit {

  private _model: RegionModel;

  constructor(private _formGroup: FormBuilder, private _messageService: MessageService) {
    this._model = new RegionModel(_formGroup);
    this._model.regionForm
  }

  ngOnInit() {
  }

  private onSubmit(formData: String): void {
    this._messageService.add({ severity: 'success', summary: 'Atention', detail: 'onSubmit'})
    console.log(formData);
  }

}
