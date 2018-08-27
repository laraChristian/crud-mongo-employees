import { Component, OnInit } from '@angular/core';
import { EmployeeService } from '../../services/employee.service';
import { EmployeesModel } from './employees.model';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { MessageService } from 'primeng/primeng';
import { AdministrativesService } from '../../services/administratives.service';

@Component({
  selector: 'employees-ui',
  templateUrl: './employees-ui.component.html',
  styleUrls: ['./employees-ui.component.css'],
  providers: [EmployeeService, AdministrativesService]
})
export class EmployeesUiComponent implements OnInit {

  private _model: EmployeesModel;

  constructor(private _formBuilder: FormBuilder, private _employeeService: EmployeeService, private _administrativeService: AdministrativesService, private _messageService: MessageService) {
    this._model = new EmployeesModel(this._formBuilder, this._messageService);
  }

  ngOnInit() {
    this.listEmployees();
    this.listManagers();
    this.listJobs();
    this.listDepartments();
  }

  //#region CORE
  create(): void {
    console.log('start -- create method')
    this._model.mapFormControlsToRequest(this._model.request.employee,
      this._model.fieldMap, ["id", "firstName", "lastName", "email", "phoneNumber", "hireDate", "identification", "administrative"],
      this._model.employeeForm);
    this._employeeService.create(this._model.request).subscribe(
      resp => {
        if (resp.success == true) {
          this._model.show('success', 'Atention', resp.message.toString());
        } else {
          this._model.show('warn', 'Atention', resp.message.toString());
        }
        this.listEmployees();
        this._model.clean();
      },
      error => {
        this._model.show('error', 'Atention', 'You shoul communicate with support');
        console.error('[ERROR] ' + error);
      }
    );
  }

  delete(): void {
    this._employeeService.delete(this._model.request).subscribe(
      resp => {
        if (resp.success == true) {
          this._model.show('success', 'Atention', resp.message.toString());
        } else {
          this._model.show('warn', 'Atention', resp.message.toString());
        }
        this.listEmployees();
        this._model.clean();
      },
      error => {
        this._model.show('error', 'Atention', 'You shoul communicate with support');
        console.error('[ERROR] ' + error);
      }
    );
  }
  //#endregion

  //#region DATA-TABLE
  private listEmployees(): void {
    this._employeeService.listEmployees().subscribe(
      resp => {
        if (resp.success == true) {
          this._model.dtblEmployees = resp.employees;
        } else {
          this._model.show('error', 'Atention', 'You should communicate with support immediately');
          console.error(resp.message)
        }
      },
      error => {
        this._model.show('error', 'Atention', 'You should communicate with support immediately');
        console.error(error)
      }
    );
  }

  private onRowSelect(): void {
    this._model.mapRequestToFormControls(this._model.request.employee,
      this._model.fieldMap, ["id", "firstName", "lastName", "email", "phoneNumber", "hireDate", "identification", "administrative"],
      this._model.employeeForm);
    this._model.request.update = true;
    this._model.disableDelete = false;
    this.onChangeJob();
  }
  //#endregion


  //#region DROP-DOWNS
  private listManagers(): void {
    this._employeeService.listCmb().subscribe(
      resp => {
        if (resp.success == true) {
          this._model.request.employee.managerId = ' ';
          this._model.fillSelectItem(resp.employees, 'firstName', 'id', this._model.managersItems, { label: 'Managers', value: '' });
        }
      }
    );
  }

  private listJobs(): void {
    this._administrativeService.listJobs().subscribe(
      resp => {
        if (resp.success == true) {
          this._model.request.employee.jobId = ' ';
          this._model.fillSelectItem(resp.jobs, 'jobTittle', 'id', this._model.jobItems, { label: 'JOB', value: '' });
        }
      }
    );
  }

  onChangeJob(): void {
    console.log('start onChange')
    let ctrl = this._model.employeeForm.get('administrative').get('managerId');
    if (this._model.request.employee.jobId.includes(this._model.jobPresidentId.toString())) {
      ctrl.setValue('0');
      ctrl.disable({ onlySelf: true, emitEvent: false });
    } else {
      ctrl.enable();
    }
  }

  private listDepartments(): void {
    this._administrativeService.listDepartments().subscribe(
      resp => {
        if (resp.success == true) {
          this._model.request.employee.departmentId = ' ';
          this._model.fillSelectItem(resp.departments, 'name', 'id', this._model.departmentItems, { label: 'Departments', value: '' });
        }
      }
    );
  }
  //#endregion


}
