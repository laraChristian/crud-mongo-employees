import { Component, OnInit } from '@angular/core';
import { DepartmentsModel } from './departments.model';
import { MessageService } from 'primeng/primeng';
import { AdministrativesService } from '../../services/administratives.service';
import { RegionService } from '../../services/region.service';
import { EmployeeService } from '../../services/employee.service';
import { Department } from '../../entities/department';

@Component({
  selector: 'departments-ui',
  templateUrl: './departments-ui.component.html',
  styleUrls: ['./departments-ui.component.css'],
  providers: [AdministrativesService, RegionService, EmployeeService]
})
export class DepartmentsUiComponent implements OnInit {

  private _model: DepartmentsModel;

  constructor(private _messageService: MessageService, private _administrativeApi: AdministrativesService,
    private _regionService: RegionService, private _employeeService: EmployeeService) {
    this._model = new DepartmentsModel();
  }

  ngOnInit() {
    this.listDepartments();
    this.listLocation();
    this.listManagers();
  }

  //#region CORE
  private create(): void {
    console.log('start -- create method')
    if (this._model.validateFields(['name'], this._model.request.department) == true) {
      this._administrativeApi.createDepartment(this._model.request).subscribe(
        resp => {
          if (resp.success == true) {
            this._messageService.add({ severity: 'success', summary: 'Atention', detail: resp.message.toString(), life: 6000 });
            this.listDepartments();
          } else {
            this._messageService.add({ severity: 'warn', summary: 'Atention', detail: resp.message.toString(), life: 6000 });
          }
        }, error => {
          this._messageService.add({ severity: 'error', summary: 'Atention', detail: 'You should coomunicate with support immediately', sticky: true });
          console.error('[ERROR] ' + error);
        }, () => console.log('end -- create method')
      );
      this.cancel();
    } else {
      this._messageService.add({ life: 6000, severity: 'error', summary: 'Atention', detail: 'empty fields aren\'t allowed' });
    }
  }
  //#endregion

  //#region DATA-TABLE
  listDepartments(): void {
    console.log('start -- list-departments method');
    this._administrativeApi.listDepartments().subscribe(
      resp => {
        if (resp.success == true) {
          this._model.departments = resp.departments;
          console.log(this._model.departments)
        }
      },
      error => {
        this._messageService.add({ life: 600, severity: 'error', summary: 'Atention', detail: 'You should communicate with support immediately' });
        console.error('[ERROR] ' + error);
      },
      () => console.log('end -- list-departments method')
    );
  }

  onRowSelect(): void {
    this._model.displayDialog = true;
    this._model.request.update = true;
    this._model.disableManagerDd = false;
  }

  //#endregion

  //#region DIALOG-FORM
  private listLocation(): void {
    this._regionService.listLocations().subscribe(
      resp => {
        if (resp.success == true) {
          this._model.request.department.locationId = '0';
          this._model.fillSelectItem(resp.locations, 'streetAddress', 'id', this._model.locationItems, { label: 'Location', value: '0' });
        }
      },
      error => {
        this._messageService.add({ life: 600, severity: 'error', summary: 'Atention', detail: 'You should communicate with support immediately' });
        console.error('[ERROR] ' + error);
      }
    );
  }

  private listManagers(): void {
    console.log('start -- list-managers')
    this._employeeService.listCmb().subscribe(
      resp => {
        if (resp.success == true) {
          this._model.fillSelectItem(resp.employees, 'firstName', 'id', this._model.managerItems, { label: 'Reject Manager', value: '0' });
        }
      },
      error => console.log('[ERROR] ' + error),
      () => console.log('end -- list-managers')
    );
  }

  private cancel(): void {
    this._model.clean();
    this._messageService.clear();
  }

  private show(): void {
    this._model.displayDialog = true;
  }
  //#endregion

  //#region DIALOG-CONFIRM-DELETE

  private showConfirm(data: Department): void {
    this._messageService.clear();
    this._messageService.add({ key: 'c', sticky: true, severity: 'warn', summary: 'Are you sure?', detail: 'Confirm to proceed' });
    this._model.mapRowToRequest(data);
  }

  private onConfirm(): void {
    console.log('start -- delete method')
    this._administrativeApi.deleteDepartment(this._model.request).subscribe(
      resp => {
        if (resp.success == true) {
          this._messageService.add({ sticky: true, severity: 'success', summary: 'Atention', detail: resp.message.toString() });
          this.listDepartments();
        } else {
          this._messageService.add({ sticky: true, severity: 'warn', summary: 'Atention', detail: resp.message.toString() });
        }
      }, error => {
        this._messageService.add({ sticky: true, severity: 'warn', summary: 'Atention', detail: 'You should coomunicate with support immediately' });
        console.error('[ERROR] ' + error);
      }, () => console.log('end -- delete method')
    );
    this.cancel();
  }

  //#endregion
}
