import { Component, OnInit } from '@angular/core';
import { JobsModel } from './jobs.model';
import { MessageService } from 'primeng/primeng';
import { Job } from '../../entities/job';
import { AdministrativesService } from '../../services/administratives.service';

@Component({
  selector: 'jobs-ui',
  templateUrl: './jobs-ui.component.html',
  styleUrls: ['./jobs-ui.component.css'],
  providers: [AdministrativesService]
})
export class JobsUiComponent implements OnInit {

  private _model: JobsModel;


  constructor(private _messageService: MessageService, private _administrativeApi: AdministrativesService) {
    this._model = new JobsModel();
  }

  ngOnInit() {
    this.listJobs();
  }

  //#region Data-Table
  private listJobs(): void {
    this._administrativeApi.listJobs().subscribe(
      resp => {
        if (resp.success == true) {
          this._model.jobsDtbl = resp.jobs;
        } else {
          this._messageService.add({ severity: 'error', summary: 'Atention', detail: 'You should communicate with support immediately' })
          console.error(resp.message)
        }
      }, error => {
        this._messageService.add({ severity: 'error', summary: 'Atention', detail: 'You should communicate with support immediately' })
        console.error('[ERROR] ' + error);
      }
    );
  }

  private selectCar(rowData: Job): void {
    this._model.request.update = true;
    this._model.disableDelete = false;
    this._model.mapRowDataToRequest(rowData);
  }

  //#endregion

  //#region CRUD
  private save(): void {
    console.log('start -- save method');
    if (this._model.validateFields(['jobTittle', 'minSalary', 'maxSalary'], this._model.request.job) == true) {
      this._administrativeApi.createJob(this._model.request).subscribe(
        resp => {
          if (resp.success == true) {
            this._messageService.add({ severity: 'success', summary: 'Atention', detail: resp.message.toString() });
            this._model.clean();
          } else {
            this._messageService.add({ severity: 'warn', summary: 'Atention', detail: resp.message.toString() });
          }
          this.listJobs();
        },
        error => {
          this._messageService.add({ severity: 'error', summary: 'Error', detail: 'You shoul communicate with support immediately' });
          console.error('[ERROR] ' + error);
        },
        () => console.log('end -- save method')
      );
    } else {
      this._messageService.add({ severity: 'error', summary: 'Error', detail: 'Empty fields arent allowed' });
    }
  }
  //#endregion
}
