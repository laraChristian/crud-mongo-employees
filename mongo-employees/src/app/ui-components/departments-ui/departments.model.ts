import { DepartmentRequest } from "../../domain/departmentRequest";
import { Department } from "../../entities/department";
import { SelectItem } from "primeng/primeng";
import { Commons } from "../ui-utilities/commons";

export class DepartmentsModel extends Commons {

    request: DepartmentRequest;
    dtblColums: any[];
    departments: Array<Department>;
    displayDialog: boolean;
    disableManagerDd:boolean;
    managerItems: Array<SelectItem>;
    locationItems: Array<SelectItem>;


    constructor() {
        super();
        this.clean();
        this.initColumns();
        this.managerItems = new Array<SelectItem>();
        this.locationItems = new Array<SelectItem>();
        this.disableManagerDd = true;
    }

    clean(): void {
        this.request = new DepartmentRequest();
        this.displayDialog = false;
        this.disableManagerDd = true;
    }

    initColumns(): void {
        this.departments = new Array<Department>();
        this.dtblColums = [{ field: 'name', header: 'Department' },
        { field: 'managerName', header: 'Manager' },
        { field: 'locationAddress', header: 'locationName' }];
    }

    mapRowToRequest(rowData: Department): void {
        this.request.update = true;
        this.request.department.id = rowData.id;
        this.request.department.locationId = rowData.locationId;
        this.request.department.managerId = rowData.managerId;
    }
}