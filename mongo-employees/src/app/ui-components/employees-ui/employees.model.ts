import { EmployeeRequest } from "../../domain/employeeRequest";
import { Employee } from "../../entities/employee";
import { Mapper } from "../../mappers/mapper";
import { FormGroup, FormBuilder, FormControl, Validators } from "@angular/forms";
import { ValueTransformer } from "@angular/compiler/src/util";
import { SelectItem, MessageService } from "primeng/primeng";
import { Commons } from "../ui-utilities/commons";

export class EmployeesModel extends Commons {

    request: EmployeeRequest;
    employeeForm: FormGroup;
    disableDelete: boolean;
    dtblCols: any[];
    dtblEmployees: Array<Employee>;
    fieldMap: Map<String, Array<String>>;
    managersItems: SelectItem[];
    jobItems: SelectItem[];
    departmentItems: SelectItem[];
    jobPresidentId: String = "5b7d7ebd15799404bae944cf";
    disableManagerDP: boolean;

    constructor(private _formBuilder: FormBuilder, private _messageService: MessageService) {
        super();
        this.initForm();
        this.clean();
        this.initColumns();
        this.managersItems = [];
        this.jobItems = [];
        this.departmentItems = [];
    }

    clean(): void {
        this.request = new EmployeeRequest();
        this.employeeForm.reset();
        this.disableDelete = true;
        if (this.employeeForm.get('administrative').get('managerId').disabled == true) {
            this.employeeForm.get('administrative').get('managerId').enable();
        }
    }

    initForm(): void {
        this.employeeForm = this._formBuilder.group({
            'id': new FormControl('', Validators.nullValidator),
            'firstName': new FormControl('', Validators.required),
            'lastName': new FormControl('', Validators.required),
            'email': this._formBuilder.group({
                'email': new FormControl('', Validators.required),
                'password': new FormControl('', Validators.required)
            }),
            'phoneNumber': new FormControl('', Validators.required),
            'hireDate': new FormControl('', Validators.nullValidator),
            'identification': new FormControl('', Validators.required),
            'administrative': this._formBuilder.group({
                'managerId': new FormControl('', Validators.required),
                'jobId': new FormControl('', Validators.required),
                'departmentId': new FormControl('', Validators.required)
            })
        });
        this.fieldMap = new Map<String, Array<String>>();
        this.fieldMap.set('id', ["id"]);
        this.fieldMap.set('firstName', ["firstName"]);
        this.fieldMap.set('lastName', ["lastName"]);
        this.fieldMap.set('email', ["email", "password"]);
        this.fieldMap.set('phoneNumber', ["phoneNumber"]);
        this.fieldMap.set('hireDate', ["hireDate"]);
        this.fieldMap.set('identification', ["identification"]);
        this.fieldMap.set('administrative', ["managerId", "jobId", "departmentId"]);
    }

    mapFormControlsToRequest(request: any, mapper: Map<String, Array<String>>, requestFields: Array<String>, form: FormGroup): void {
        console.info('start map-to-request method')
        requestFields.forEach(field => {
            let fields = mapper.get(field);
            if (fields.length == 1) {
                request[field.toString()] = form.controls[field.toString()].value;
            } else {
                fields.forEach(value => {
                    request[value.toString()] = form.controls[field.toString()].value[value.toString()];
                })
            }
        });
    }

    mapRequestToFormControls(request: any, mapper: Map<String, Array<String>>, requestFields: Array<String>, form: FormGroup): void {
        console.info('start map-to-request method')
        requestFields.forEach(field => {
            let fields = mapper.get(field);
            if (fields.length == 1) {
                form.controls[field.toString()].setValue(request[field.toString()]);
            } else {
                fields.forEach(value => {
                    this.employeeForm.controls[field.toString()].get(value.toString()).setValue(request[value.toString()]);
                })
            }
        });
    }

    initColumns(): void {
        this.dtblCols = [{ field: 'firstName', header: 'First Name' }, { field: 'lastName', header: 'Last Name' },
        { field: 'email', header: 'User Mail' }, { field: 'phoneNumber', header: 'Phone Number' },
        { field: 'identification', header: 'Identification' }, { field: 'managerName', header: 'Manager' },
        { field: 'jobTittle', header: 'Job' }, { field: 'departmentName', header: 'Department' }]
    }

    show(severity: string, summary: string, detail: string): void {
        this._messageService.clear();
        this._messageService.add({ severity: severity, summary: summary, detail: detail, life: 3000 })
    }
}