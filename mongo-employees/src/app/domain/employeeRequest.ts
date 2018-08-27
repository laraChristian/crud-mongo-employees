import { Employee } from "../entities/employee";

export class EmployeeRequest {

    update: boolean;
    employee: Employee;

    constructor() {
        this.update = false;
        this.employee = new Employee();
    }

}