import { Department } from "../entities/department";

export class DepartmentRequest {

    public update: boolean;
    public department: Department;

    constructor() {
        this.update = false;
        this.department = new Department();
    }
}